package net.twasi.twitchapi.config;

public class Constants {

    // TMI
    public static final String TMI_CHATTERS_URL = "http://tmi.twitch.tv/group/user/%s/chatters";

    // ID
    public static final String ID_AUTH_URL = "https://id.twitch.tv/oauth2/authorize" +
            "?client_id=%s" +
            "&redirect_uri=%s" +
            "&response_type=%s" +
            "&scope=%s" +
            "&state=%s";

    public static String getIDAuthUrl(String clientId, String redirectUri, String responseType, String scope, String state) {
        return String.format(ID_AUTH_URL, clientId, redirectUri, responseType, scope, state);
    }

    public static final String ID_TOKEN_URL = "https://id.twitch.tv/oauth2/token" +
            "?client_id=%s" +
            "&client_secret=%s" +
            "&code=%s" +
            "&grant_type=authorization_code" +
            "&redirect_uri=%s";
    public static String getTokenAuthUrl(String clientId, String clientSecret, String code, String redirectUri) {
        return String.format(ID_TOKEN_URL, clientId, clientSecret, code, redirectUri);
    }

    public static final String ID_TOKEN_REFRESH_URL = "https://id.twitch.tv/oauth2/token" +
            "?grant_type=refresh_token" +
            "&refresh_token=%s" +
            "&client_id=%s" +
            "&client_secret=%s";
    public static String getTokenRefreshAuthUrl(String clientId, String clientSecret, String refreshToken) {
        return String.format(ID_TOKEN_REFRESH_URL, refreshToken, clientId, clientSecret);
    }

    // Helix
    public static final String HELIX = "https://api.twitch.tv/helix";
    public static final String HELIX_USERS = HELIX + "/users";
    public static final String HELIX_USERS_FOLLOW = HELIX_USERS + "/follows";
    public static final String HELIX_STREAMS = HELIX + "/streams";

    // Kraken
    public static final String KRAKEN = "https://api.twitch.tv/kraken";
    public static final String KRAKEN_CHANNELS = KRAKEN + "/channels";
    //public static final String KRAKEN_CHANNELS = "http://requestbin.fullcontact.com/182a3ba1";
}
