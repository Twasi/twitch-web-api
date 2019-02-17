package net.twasi.twitchapi.id.oauth2.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenValidationDTO {
    @JsonProperty("client_id")
    private String clientId;

    private String login;

    private String[] scopes;

    @JsonProperty("user_id")
    private String userId;

    public String getClientId() {
        return clientId;
    }

    public String getLogin() {
        return login;
    }

    public String[] getScopes() {
        return scopes;
    }

    public String getUserId() {
        return userId;
    }
}
