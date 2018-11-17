package net.twasi.twitchapi.helix.users;

import net.twasi.twitchapi.auth.AuthenticationType;
import net.twasi.twitchapi.auth.AuthorizationContext;
import net.twasi.twitchapi.config.Constants;
import net.twasi.twitchapi.exception.InvalidAuthContextException;
import net.twasi.twitchapi.exception.RequestException;
import net.twasi.twitchapi.helix.HelixResponseWrapper;
import net.twasi.twitchapi.requests.RestClient;
import net.twasi.twitchapi.helix.users.response.*;
import net.twasi.twitchapi.requests.RestClientResponse;

import java.util.logging.Logger;

public class Users {
    private String type = "twasi.twitchapi.helix.users";
    private Logger logger = Logger.getLogger(type);
    private RestClient client;
    private AuthorizationContext ctx;

    public Users(RestClient client, AuthorizationContext ctx) {
        if (ctx.getType() != AuthenticationType.CLIENTID) {
            throw new InvalidAuthContextException("Users endpoints need Auth Context Type 'CLIENTID', '" + ctx.getType() + "' supplied.");
        }

        this.client = client;
        this.ctx = ctx;
    }

    public UserDTO getCurrentUser(String accessToken) {
        try {
            RestClientResponse<HelixResponseWrapper<UserDTO>> response = client.get(UserDTO.WrappedUserDTO.class, Constants.HELIX_USERS, accessToken);

            return response.getResponse().getData().get(0);
        } catch (RequestException e) {
            logger.severe(String.format("Request %s/getCurrentUser failed for access token %s: %s", type, accessToken, e.getMessage()));
            return null;
        }
    }

}
