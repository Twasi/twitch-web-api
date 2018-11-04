package net.twasi.twitchapi;

import net.twasi.twitchapi.auth.ClientIdCredentials;
import org.junit.jupiter.api.Test;

public class TwitchAPITest {

    @Test
    void buildsMinimal() {
        TwitchAPI api = new TwitchAPI.Builder().usingCredentials(new ClientIdCredentials("clientId")).build();
    }

}
