package net.twasi.twitchapi;

import net.twasi.twitchapi.auth.ClientIdCredentials;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TwitchAPITest {

    @Test
    void buildsMinimal() {
        TwitchAPI api = new TwitchAPI.Builder().usingCredentials(new ClientIdCredentials("clientId")).build();
    }

    @Test
    void failsWithoutCredentials() {
        TwitchAPI.Builder builder = new TwitchAPI.Builder();

        RuntimeException e = Assertions.assertThrows(RuntimeException.class, builder::build);

        Assertions.assertEquals("Credentials must be provided before building.", e.getMessage());
    }

}
