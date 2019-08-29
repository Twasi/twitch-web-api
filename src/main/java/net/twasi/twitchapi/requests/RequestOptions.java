package net.twasi.twitchapi.requests;

import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import net.twasi.twitchapi.auth.AuthorizationContext;
import net.twasi.twitchapi.auth.PersonalAuthorizationContext;
import net.twasi.twitchapi.exception.RejectionReason;
import net.twasi.twitchapi.exception.RejectionSolveMethod;
import net.twasi.twitchapi.logging.LoggingConfigurator;
import net.twasi.twitchapi.options.TwitchRequestOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class RequestOptions {
    private PersonalAuthorizationContext ctx;
    private AuthorizationContext generalCtx;
    private boolean v5Header = false;
    private String body;
    private TwitchRequestOptions options;

    private String type = "twasi.twitchapi.requestoptions";
    private Logger logger = LoggingConfigurator.getLogger(type);

    // Request information
    private List<QueryStringParameter> queryString = new ArrayList<>();
    private Map<String, String> customHeaders = new HashMap<>();

    private int retries = 0;
    private int maxRetries = 2;
    private boolean shouldRetry = true;
    private boolean failSilently = false;

    public RequestOptions withPersonalAuth(PersonalAuthorizationContext personalCtx) {
        this.ctx = personalCtx;
        return this;
    }

    public RequestOptions withV5() {
        v5Header = true;
        return this;
    }

    public RequestOptions withClientId(AuthorizationContext ctx) {
        generalCtx = ctx;
        return this;
    }

    public RequestOptions withQueryString(String key, String value) {
        queryString.add(new QueryStringParameter(key, value));
        return this;
    }

    public RequestOptions withJsonBody(String obj) {
        this.body = obj;
        return this;
    }

    public RequestOptions dontRetry() {
        shouldRetry = false;
        return this;
    }

    public RequestOptions failSilently() {
        failSilently = true;
        return this;
    }

    public boolean shouldFailSilently() {
        return failSilently;
    }

    public RequestOptions withRequestOptions(TwitchRequestOptions options) {
        this.options = options;
        // Also override context here for better compatibility
        if (this.ctx == null && options.getPersonalCtx() != null) {
            this.ctx = options.getPersonalCtx();
        }
        return this;
    }

    public RequestOptions withHeader(String key, String value) {
        customHeaders.put(key, value);
        return this;
    }

    public int getRetries() {
        return retries;
    }

    void apply(HttpRequest request) {
        if (options != null) {
            if (options.getPersonalCtx() != null) {
                request.header("Authorization", "Bearer " + options.getPersonalCtx().getAccessToken());
            }
        }

        if (ctx != null) {
            if (v5Header) {
                request.getHeaders().remove("Authorization");
                request.header("Authorization", "OAuth " + ctx.getAccessToken());
            } else {
                request.getHeaders().remove("Authorization");
                request.header("Authorization", "Bearer " + ctx.getAccessToken());
            }
        }

        for (QueryStringParameter param : queryString) {
            request.queryString(param.getKey(), param.getValue());
        }

        if (body != null) {
            HttpRequestWithBody req = (HttpRequestWithBody) request;
            req.body(body);
            req.header("Content-Type", "application/json");
        }

        if (generalCtx != null) {
            request.header("Client-ID", generalCtx.getClientId());
        }

        if (v5Header) {
            request.header("Accept", "application/vnd.twitchtv.v5+json");
            //request.header("Content-Type", "application/json");
        }

        // Custom headers
        for (Map.Entry<String, String> entry : customHeaders.entrySet()) {
            request.header(entry.getKey(), entry.getValue());
        }
    }

    RejectionSolveMethod handleRejection(RejectionReason reason) {
        if (retries >= maxRetries || !shouldRetry) {
            logger.info("Failed to refresh token using provided context. Max retries reached.");
            return RejectionSolveMethod.FAIL;
        }

        // if it is related to auth, try to refresh the token
        if (reason == RejectionReason.UNAUTHORIZED) {
            if (ctx != null) {
                if (ctx.autoRefresh()) {
                    // This counts as "retry"
                    retries++;
                    return RejectionSolveMethod.RETRY;
                } else {
                    logger.info("Failed to refresh token using provided context. Is the connection revoked?");
                }
            } else if (options != null && options.getPersonalCtx() != null) {
                if (ctx.autoRefresh()) {
                    retries++;
                    return RejectionSolveMethod.RETRY;
                } else {
                    logger.info("Failed to refresh token using provided personal/optional context. Is the connection revoked?");
                }
            } else {
                logger.info("Failed to refresh token, no context provided!");
            }

            // Fail if problem is unauthorized but no context is supplied
            return RejectionSolveMethod.FAIL;
        }

        // any other fails, retry one time (temporary API issue)
        retries++;
        return RejectionSolveMethod.RETRY;
    }

    public PersonalAuthorizationContext getAuthContext() {
        return ctx;
    }

    public TwitchRequestOptions getTwitchRequestOptions() {
        return options;
    }

    static class QueryStringParameter {
        String key;
        String value;

        public QueryStringParameter(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

}
