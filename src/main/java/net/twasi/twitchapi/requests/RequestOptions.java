package net.twasi.twitchapi.requests;

import com.mashape.unirest.request.HttpRequest;

public class RequestOptions {
    private String authHeader;
    private boolean v5Header = false;

    public RequestOptions withBearerToken(String accessToken) {
        if (authHeader != null) {
            throw new IllegalStateException("Can only use one authentication method at a time.");
        }
        authHeader = "Bearer " + accessToken;
        return this;
    }

    public RequestOptions withOAuthToken(String accessToken) {
        if (authHeader != null) {
            throw new IllegalStateException("Can only use one authentication method at a time.");
        }
        authHeader = "OAuth " + accessToken;
        return this;
    }

    public RequestOptions withV5Header() {
        v5Header = true;
        return this;
    }

    void apply(HttpRequest request) {
        if (authHeader != null) {
            request.header("Authorization", authHeader);
        }

        if (v5Header) {
            request.header("Accept", "application/vnd.twitchtv.v5+json");
        }
    }

}
