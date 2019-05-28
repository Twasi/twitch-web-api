package net.twasi.twitchapi.helix.games.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.twasi.twitchapi.helix.HelixResponseWrapper;

public class GameDTO {

    private String id;

    private String name;

    @JsonProperty("box_art_url")
    private String boxArtUrl;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBoxArtUrl() {
        return boxArtUrl;
    }

    public static class WrappedUserDTO extends HelixResponseWrapper<GameDTO> {
    }
}
