package net.twasi.twitchapi.auth;

import net.twasi.twitchapi.TwitchAPI;
import net.twasi.twitchapi.id.oauth2.response.TokenDTO;

public class PersonalAuthorizationContext {
    private String accessToken;
    private String refreshToken;

    private String twitchId;

    private AuthenticationType type;

    public PersonalAuthorizationContext(String accessToken, String refreshToken, String twitchId, AuthenticationType type) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.twitchId = twitchId;
        this.type = type;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public AuthenticationType getType() {
        return type;
    }

    public String getTwitchId() {
        return twitchId;
    }

    public boolean autoRefresh() {
        TokenDTO token = TwitchAPI.authentication().refreshToken(refreshToken);

        if (token == null) {
            return false;
        }

        this.accessToken = token.getAccessToken();
        this.refreshToken = token.getRefreshToken();

        onRefresh();

        return true;
    }

    public boolean isValid() {
        return TwitchAPI.authentication().isValid(accessToken);
    }

    /**
     * If you need to catch the event of a token refreshing,
     * create a subclass and override this method.
     *
     * This is eg useful if you have to update the token in the database.
     */
    public void onRefresh() {
    }
}
