package net.twasi.twitchapi.requests;

import com.mashape.unirest.request.HttpRequest;
import net.twasi.twitchapi.auth.AuthenticationType;
import net.twasi.twitchapi.auth.PersonalAuthorizationContext;
import net.twasi.twitchapi.exception.RejectionReason;
import net.twasi.twitchapi.exception.RejectionSolveMethod;

public class RequestOptions {
    private PersonalAuthorizationContext ctx;
    private boolean v5Header = false;

    private int retries = 0;

    public RequestOptions withPersonalAuth(PersonalAuthorizationContext personalCtx) {
        this.ctx = personalCtx;
        return this;
    }

    public RequestOptions withV5Header() {
        v5Header = true;
        return this;
    }

    void apply(HttpRequest request) {
        if (ctx != null) {
            if (ctx.getType() == AuthenticationType.BEARER) {
                request.header("Authorization", "Bearer " + ctx.getAccessToken());
            } else if (ctx.getType() == AuthenticationType.OAUTH) {
                request.header("Authorization", "OAuth " + ctx.getAccessToken());
            }
        }

        if (v5Header) {
            request.header("Accept", "application/vnd.twitchtv.v5+json");
        }
    }

    RejectionSolveMethod handleRejection(RejectionReason reason) {
        // if it is related to auth, try to refresh the token
        if (reason == RejectionReason.UNAUTHORIZED) {
            if (ctx != null) {
                if (ctx.autoRefresh()) {
                    return RejectionSolveMethod.RETRY;
                }
            }
            // Fail if problem is unauthorized but no context is supplied
            return RejectionSolveMethod.FAIL;
        }

        // any other fails, retry one time (temporary API issue)
        if (retries == 0) {
            retries++;
            return RejectionSolveMethod.RETRY;
        }

        // lastly, fail
        return RejectionSolveMethod.FAIL;
    }

    public PersonalAuthorizationContext getAuthContext() {
        return ctx;
    }

}
