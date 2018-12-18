package net.twasi.twitchapi.kraken;

import net.twasi.twitchapi.auth.AuthorizationContext;
import net.twasi.twitchapi.kraken.channels.Channels;
import net.twasi.twitchapi.requests.RestClient;

public class Kraken {
    private Channels channels;

    private RestClient client;
    private AuthorizationContext ctx;

    public Kraken(RestClient client, AuthorizationContext authContext) {
        this.client = client;
        this.ctx = authContext;

        channels = new Channels(client, ctx);
    }

    public Channels channels() {
        return channels;
    }
}
