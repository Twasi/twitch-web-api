package net.twasi.twitchapi.unit.id.oauth2;

import net.twasi.twitchapi.auth.AuthorizationContext;
import net.twasi.twitchapi.id.oauth2.Authentication;
import net.twasi.twitchapi.id.oauth2.response.TokenDTO;
import net.twasi.twitchapi.mock.MockClient;
import net.twasi.twitchapi.requests.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AuthenticationTest {

    @Test
    public void returnsAuthorizationUrl() {
        AuthorizationContext ctx = new AuthorizationContext("clientId", "clientSecret", "redirectUri");

        Authentication auth = new Authentication(null, ctx);

        String authorizationUrl = auth.getAuthURL("scope", "state");

        Assertions.assertEquals("https://id.twitch.tv/oauth2/authorize?client_id=clientId&redirect_uri=redirectUri&response_type=code&scope=scope&state=state", authorizationUrl);
    }

    @Test
    public void returnsTokenByCode() {
        AuthorizationContext ctx = new AuthorizationContext("clientId", "clientSecret", "redirectUri");

        RestClient mocked = new MockClient("oauth/getToken");

        Authentication auth = new Authentication(mocked, ctx);

        TokenDTO token = auth.getToken("code");

        Assertions.assertEquals("userAccessToken", token.getAccessToken());
        Assertions.assertEquals("userRefreshToken", token.getRefreshToken());
        Assertions.assertEquals("tokenType", token.getTokenType());
        Assertions.assertEquals(1337, token.getExpiresIn());

        Assertions.assertEquals(2, token.getScope().length);
        Assertions.assertEquals("scope1", token.getScope()[0]);
        Assertions.assertEquals("scope2", token.getScope()[1]);
    }

    @Test
    public void refreshesToken() {
        AuthorizationContext ctx = new AuthorizationContext("clientId", "clientSecret", "redirectUri");

        RestClient mocked = new MockClient("oauth/refreshToken");

        Authentication auth = new Authentication(mocked, ctx);

        TokenDTO token = auth.refreshToken("refreshToken");

        Assertions.assertEquals("userAccessToken", token.getAccessToken());
        Assertions.assertEquals("userRefreshToken", token.getRefreshToken());

        Assertions.assertEquals(2, token.getScope().length);
        Assertions.assertEquals("scope1", token.getScope()[0]);
        Assertions.assertEquals("scope2", token.getScope()[1]);
    }

}
