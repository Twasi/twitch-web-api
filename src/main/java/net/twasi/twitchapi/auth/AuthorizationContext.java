package net.twasi.twitchapi.auth;

public class AuthorizationContext {
    private String clientId;
    private String clientSecret;
    private String redirectUri;

    private AuthenticationType type;

    public AuthorizationContext(String clientId, String clientSecret, String redirectUri) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;

        this.type = AuthenticationType.CLIENTID;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public AuthenticationType getType() {
        return type;
    }
}
