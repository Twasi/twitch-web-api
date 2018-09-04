package net.twasi.twitchapi.auth;

public abstract class ApiCredentials {
    private AuthenticationType authType;

    public ApiCredentials(AuthenticationType authType) {
        this.authType = authType;
    }
}
