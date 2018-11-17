package net.twasi.twitchapi.id.oauth2;

import net.twasi.twitchapi.auth.AuthenticationType;
import net.twasi.twitchapi.auth.AuthorizationContext;
import net.twasi.twitchapi.config.Constants;
import net.twasi.twitchapi.exception.InvalidAuthContextException;
import net.twasi.twitchapi.exception.RequestException;
import net.twasi.twitchapi.id.oauth2.response.TokenDTO;
import net.twasi.twitchapi.requests.RestClient;
import net.twasi.twitchapi.requests.RestClientResponse;

import java.util.logging.Logger;

public class Authentication {
    private String type = "twasi.twitchapi.id.oauth2";
    private Logger logger = Logger.getLogger(type);
    private RestClient client;
    private AuthorizationContext ctx;

    public Authentication(RestClient client, AuthorizationContext ctx) {
        if (ctx.getType() != AuthenticationType.CLIENTID) {
            throw new InvalidAuthContextException("Authentication endpoints need Auth Context Type 'CLIENTID', '" + ctx.getType() + "' supplied.");
        }

        this.client = client;
        this.ctx = ctx;
    }

    public String getAuthURL(String scope, String state) {
        return Constants.getIDAuthUrl(ctx.getClientId(), ctx.getRedirectUri(), "code", scope, state);
    }

    public TokenDTO getToken(String code) {
        try {
            RestClientResponse<TokenDTO> response = client.get(TokenDTO.class, Constants.getTokenAuthUrl(ctx.getClientId(), ctx.getClientSecret(), code, ctx.getRedirectUri()));

            return response.getResponse();
        } catch (RequestException e) {
            logger.severe(String.format("Request %s/getToken failed for code %s: %s", type, code, e.getMessage()));
            return null;
        }
    }

    public TokenDTO refreshToken(String refreshToken) {
        try {
            RestClientResponse<TokenDTO> response = client.post(TokenDTO.class, Constants.getTokenRefreshAuthUrl(ctx.getClientId(), ctx.getClientSecret(), refreshToken));

            return response.getResponse();
        } catch (RequestException e) {
            logger.severe(String.format("Request %s/refreshToken failed for refresh token %s: %s", type, refreshToken, e.getMessage()));
            return null;
        }
    }
}
