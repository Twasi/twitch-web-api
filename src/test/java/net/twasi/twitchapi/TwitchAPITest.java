package net.twasi.twitchapi;

import org.junit.jupiter.api.Test;

public class TwitchAPITest {

    @Test
    void buildsMinimal() {
        TwitchAPI api = new TwitchAPI.Builder().build();
    }

}
