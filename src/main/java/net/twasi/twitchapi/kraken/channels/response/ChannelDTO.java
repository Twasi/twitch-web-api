package net.twasi.twitchapi.kraken.channels.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class ChannelDTO {

    @JsonProperty("_id")
    private int id;

    @JsonProperty("broadcaster_language")
    private String broadcasterLanguage;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("display_name")
    private String displayName;

    private int followers;

    private String game;

    private String language;

    private String logo;

    private Boolean mature;

    private String name;

    private Boolean partner;

    @JsonProperty("profile_banner")
    private String profileBanner;

    @JsonProperty("profile_banner_background")
    private String profileBannerBackground;

    private String status;

    @JsonProperty("updated_at")
    private Date updatedAt;

    private String url;

    @JsonProperty("video_banner")
    private String videoBanner;

    private int views;

    @JsonProperty("broadcaster_software")
    private String broadcasterSoftware;

    @JsonProperty("profile_banner_background_color")
    private String profileBannerBackgroundColor;

    @JsonProperty("broadcaster_type")
    private String broadcasterType;

    private String description;

    @JsonProperty("private_video")
    private boolean privateVideo;

    @JsonProperty("privacy_options_enabled")
    private boolean privacyOptionsEnabled;

    public int getId() {
        return id;
    }

    public String getBroadcasterLanguage() {
        return broadcasterLanguage;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getFollowers() {
        return followers;
    }

    public String getGame() {
        return game;
    }

    public String getLanguage() {
        return language;
    }

    public String getLogo() {
        return logo;
    }

    public Boolean getMature() {
        return mature;
    }

    public String getName() {
        return name;
    }

    public Boolean getPartner() {
        return partner;
    }

    public String getProfileBanner() {
        return profileBanner;
    }

    public String getProfileBannerBackground() {
        return profileBannerBackground;
    }

    public String getStatus() {
        return status;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getUrl() {
        return url;
    }

    public String getVideoBanner() {
        return videoBanner;
    }

    public int getViews() {
        return views;
    }

    public String getBroadcasterSoftware() {
        return broadcasterSoftware;
    }

    public String getProfileBannerBackgroundColor() {
        return profileBannerBackgroundColor;
    }

    public String getBroadcasterType() {
        return broadcasterType;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPrivateVideo() {
        return privateVideo;
    }

    public boolean isPrivacyOptionsEnabled() {
        return privacyOptionsEnabled;
    }
}
