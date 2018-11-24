package net.twasi.twitchapi.id.oauth2;

import net.twasi.twitchapi.auth.AuthorizationContext;
import net.twasi.twitchapi.config.Constants;
import net.twasi.twitchapi.id.oauth2.response.TokenDTO;
import net.twasi.twitchapi.requests.RestClient;
import net.twasi.twitchapi.requests.RestClientResponse;

public class Authentication {
    private RestClient client;
    private AuthorizationContext ctx;

    public Authentication(RestClient client, AuthorizationContext ctx) {
        this.client = client;
        this.ctx = ctx;
    }

    public String getAuthURL(String scope, String state) {
        return Constants.getIDAuthUrl(ctx.getClientId(), ctx.getRedirectUri(), "code", scope, state);
    }

    public TokenDTO getToken(String code) {
        RestClientResponse<TokenDTO> response = client.post(TokenDTO.class, Constants.getTokenAuthUrl(ctx.getClientId(), ctx.getClientSecret(), code, ctx.getRedirectUri()));

        return response.getResponse();
    }

    public TokenDTO refreshToken(String refreshToken) {
        RestClientResponse<TokenDTO> response = client.post(TokenDTO.class, Constants.getTokenRefreshAuthUrl(ctx.getClientId(), ctx.getClientSecret(), refreshToken));

        return response.getResponse();
    }
}
