package net.twasi.twitchapi.unit.tmi.chatters;

import net.twasi.twitchapi.mock.MockClient;
import net.twasi.twitchapi.tmi.Tmi;
import net.twasi.twitchapi.tmi.chatters.response.ChattersDTO;
import org.junit.jupiter.api.Test;

public class TmiTest {

    @Test
    public void getChatters() {
        Tmi tmi = new Tmi(new MockClient("tmi/chatters/getByName"));

        ChattersDTO response = tmi.chatters().getByName("larcce");
    }

}
