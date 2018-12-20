package net.twasi.twitchapi.requests;

import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import net.twasi.twitchapi.auth.AuthorizationContext;
import net.twasi.twitchapi.auth.PersonalAuthorizationContext;
import net.twasi.twitchapi.exception.RejectionReason;
import net.twasi.twitchapi.exception.RejectionSolveMethod;

import java.util.ArrayList;
import java.util.List;

public class RequestOptions {
    private PersonalAuthorizationContext ctx;
    private AuthorizationContext generalCtx;
    private boolean v5Header = false;
    private String body;

    // Request information
    private List<QueryStringParameter> queryString = new ArrayList<>();

    private int retries = 0;
    private int maxRetries = 2;
    private boolean shouldRetry = true;

    public RequestOptions withPersonalAuth(PersonalAuthorizationContext personalCtx) {
        this.ctx = personalCtx;
        return this;
    }

    public RequestOptions withV5(AuthorizationContext ctx) {
        v5Header = true;
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

    public int getRetries() {
        return retries;
    }

    void apply(HttpRequest request) {
        if (ctx != null) {
            if (!v5Header) {
                request.header("Authorization", "Bearer " + ctx.getAccessToken());
            } else {
                request.header("Authorization", "OAuth " + ctx.getAccessToken());
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

        if (v5Header) {
            request.header("Accept", "application/vnd.twitchtv.v5+json");
            //request.header("Content-Type", "application/json");
            request.header("Client-ID", generalCtx.getClientId());
        }
    }

    RejectionSolveMethod handleRejection(RejectionReason reason) {
        if (retries >= maxRetries) {
            return RejectionSolveMethod.FAIL;
        }

        // if it is related to auth, try to refresh the token
        if (reason == RejectionReason.UNAUTHORIZED) {
            if (ctx != null) {
                if (ctx.autoRefresh()) {
                    // This counts as "retry"
                    retries++;
                    return RejectionSolveMethod.RETRY;
                }
            }
            // Fail if problem is unauthorized but no context is supplied
            return RejectionSolveMethod.FAIL;
        }

        // any other fails, retry one time (temporary API issue)
        if (shouldRetry) {
            retries++;
            return RejectionSolveMethod.RETRY;
        }

        // lastly, fail
        return RejectionSolveMethod.FAIL;
    }

    public PersonalAuthorizationContext getAuthContext() {
        return ctx;
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
