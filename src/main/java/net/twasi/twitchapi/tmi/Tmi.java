package net.twasi.twitchapi.tmi;

import net.twasi.twitchapi.requests.RestClient;
import net.twasi.twitchapi.tmi.chatters.Chatters;

public class Tmi {
    private Chatters chatters;

    public Tmi(RestClient client) {
        // No auth required
        chatters = new Chatters(client);
    }

    public Chatters chatters() {
        return chatters;
    }

}
