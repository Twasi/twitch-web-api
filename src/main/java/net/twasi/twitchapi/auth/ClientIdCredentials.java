package net.twasi.twitchapi.auth;

public class ClientIdCredentials extends ApiCredentials {
    private String clientId;

    public ClientIdCredentials(String clientId) {
        super(AuthenticationType.CLIENTID);

        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }
}

