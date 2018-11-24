package net.twasi.twitchapi.tmi;

import net.twasi.twitchapi.requests.RestClient;
import net.twasi.twitchapi.tmi.chatters.Chatters;

public class Tmi {
    private Chatters chatters;

    private RestClient client;

    public Tmi(RestClient client) {
        this.client = client;

        // No auth required
        chatters = new Chatters(client);
    }

    public Chatters chatters() {
        return chatters;
    }

}
