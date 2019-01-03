package net.twasi.twitchapi.helix.streams.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.twasi.twitchapi.helix.HelixResponseWrapper;

import java.util.Date;
import java.util.List;

public class StreamDTO {

    private String id;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("game_id")
    private String gameId;

    @JsonProperty("community_ids")
    private List<String> communityIds;

    private String type;

    private String title;

    @JsonProperty("viewer_count")
    private int viewerCount;

    @JsonProperty("started_at")
    private Date startedAt;

    private String language;

    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;

    @JsonProperty("tag_ids")
    private List<String> tagIds;

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getGameId() {
        return gameId;
    }

    public List<String> getCommunityIds() {
        return communityIds;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public int getViewerCount() {
        return viewerCount;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public String getLanguage() {
        return language;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public List<String> getTagIds() {
        return tagIds;
    }

    public static class WrappedStreamDTO extends HelixResponseWrapper<StreamDTO> {
    }
}
