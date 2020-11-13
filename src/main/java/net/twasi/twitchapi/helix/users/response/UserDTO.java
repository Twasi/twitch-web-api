package net.twasi.twitchapi.helix.users.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.twasi.twitchapi.helix.HelixResponseWrapper;

import java.util.Date;

public class UserDTO {
    private String id;

    private String login;

    @JsonProperty("display_name")
    private String displayName;

    private String type;

    @JsonProperty("broadcaster_type")
    private String broadcasterType;

    private String description;

    @JsonProperty("profile_image_url")
    private String profileImageUrl;

    @JsonProperty("offline_image_url")
    private String offlineImageUrl;

    @JsonProperty("view_count")
    private int viewCount;

    @JsonProperty("created_at")
    private Date createdAt;

    private String email;

    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getType() {
        return type;
    }

    public String getBroadcasterType() {
        return broadcasterType;
    }

    public String getDescription() {
        return description;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getOfflineImageUrl() {
        return offlineImageUrl;
    }

    public int getViewCount() {
        return viewCount;
    }

    public String getEmail() {
        return email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public static class WrappedUserDTO extends HelixResponseWrapper<UserDTO> {
    }
}
