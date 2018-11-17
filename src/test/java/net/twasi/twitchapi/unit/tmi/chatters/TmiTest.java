package net.twasi.twitchapi.unit.tmi.chatters;

import net.twasi.twitchapi.mock.MockClient;
import net.twasi.twitchapi.tmi.Tmi;
import net.twasi.twitchapi.tmi.chatters.response.ChattersDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TmiTest {

    @Test
    public void getChatters() {
        MockClient mocked = new MockClient("tmi/chatters/getByName");

        Tmi tmi = new Tmi(mocked);

        ChattersDTO response = tmi.chatters().getByName("larcce");

        Assertions.assertEquals(6, response.getChatterCount());

        // Vips
        Assertions.assertEquals("testvip", response.getChatters().getVips().get(0));

        // Admins
        Assertions.assertEquals("testadmin", response.getChatters().getAdmins().get(0));

        // GlobalMods
        Assertions.assertEquals("testglobalmod", response.getChatters().getGlobalMods().get(0));

        // Moderators
        Assertions.assertEquals("testmoderator", response.getChatters().getModerators().get(0));

        // Staff
        Assertions.assertEquals("teststaff", response.getChatters().getStaff().get(0));

        // Viewers
        Assertions.assertEquals("testviewer", response.getChatters().getViewers().get(0));

        Assertions.assertEquals(6, response.getChatters().getAll().size());

    }

}
