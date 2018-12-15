package net.twasi.twitchapi.helix.users.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.twasi.twitchapi.helix.HelixResponseWrapper;

import java.util.Date;

public class UserFollowDTO {

    @JsonProperty("from_id")
    private String fromId;

    @JsonProperty("from_name")
    private String fromName;

    @JsonProperty("to_id")
    private String toId;

    @JsonProperty("to_name")
    private String toName;

    @JsonProperty("followed_at")
    private Date followedAt;

    public String getFromId() {
        return fromId;
    }

    public String getFromName() {
        return fromName;
    }

    public String getToId() {
        return toId;
    }

    public String getToName() {
        return toName;
    }

    public Date getFollowedAt() {
        return followedAt;
    }

    public static class WrappedUserFollowDTO extends HelixResponseWrapper<UserFollowDTO> {
    }
}
