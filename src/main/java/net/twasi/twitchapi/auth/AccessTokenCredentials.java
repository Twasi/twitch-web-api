package net.twasi.twitchapi.auth;

public class AccessTokenCredentials extends ApiCredentials {
    private String accessToken;

    public AccessTokenCredentials(String accessToken) {
        super(AuthenticationType.ACCESSTOKEN);

        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
