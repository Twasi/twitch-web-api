package net.twasi.twitchapi.helix.users;

import net.twasi.twitchapi.auth.AuthorizationContext;
import net.twasi.twitchapi.auth.PersonalAuthorizationContext;
import net.twasi.twitchapi.config.Constants;
import net.twasi.twitchapi.exception.RequestException;
import net.twasi.twitchapi.helix.HelixResponseWrapper;
import net.twasi.twitchapi.options.TwitchRequestOptions;
import net.twasi.twitchapi.requests.RequestOptions;
import net.twasi.twitchapi.requests.RestClient;
import net.twasi.twitchapi.helix.users.response.*;
import net.twasi.twitchapi.requests.RestClientResponse;

import java.util.List;

public class Users {
    private RestClient client;
    private AuthorizationContext ctx;

    public Users(RestClient client, AuthorizationContext ctx) {
        this.client = client;
        this.ctx = ctx;
    }

    public UsersWithAuth withAuth(PersonalAuthorizationContext personalCtx) {
        return new UsersWithAuth(personalCtx);
    }

    public List<UserDTO> getUsers(String[] ids, String[] logins, TwitchRequestOptions requestOptions) {
        if (ids == null) {
            ids = new String[0];
        }

        if (logins == null) {
            logins = new String[0];
        }

        if (ids.length + logins.length > 100) {
            throw new RuntimeException("You may not supply more than 100 ids/logins in total to getUsers. Maybe this will be implemented in the future.");
        }

        RequestOptions options = new RequestOptions()
                .withClientId(ctx)
                .withRequestOptions(requestOptions);

        for(String id : ids) {
            options.withQueryString("id", id);
        }

        for(String login : logins) {
            options.withQueryString("login", login);
        }

        RestClientResponse<HelixResponseWrapper<UserDTO>> response = client.get(UserDTO.WrappedUserDTO.class, Constants.HELIX_USERS, options);

        return response.getResponse().getData();
    }

    public class UsersWithAuth {
        private PersonalAuthorizationContext personalCtx;

        UsersWithAuth(PersonalAuthorizationContext personalCtx) {
            this.personalCtx = personalCtx;
        }

        public UserDTO getCurrentUser() {
            RequestOptions options = new RequestOptions().withPersonalAuth(personalCtx);

            RestClientResponse<HelixResponseWrapper<UserDTO>> response = client.get(UserDTO.WrappedUserDTO.class, Constants.HELIX_USERS, options);

            return response.getResponse().getData().get(0);
        }

        public UserFollowDTO getFollowedBy(String fromUserId) {
            RequestOptions options = new RequestOptions()
                    .withPersonalAuth(personalCtx)
                    .withQueryString("from_id", fromUserId)
                    .withQueryString("to_id", personalCtx.getTwitchId());

            RestClientResponse<HelixResponseWrapper<UserFollowDTO>> response = client.get(UserFollowDTO.WrappedUserFollowDTO.class, Constants.HELIX_USERS_FOLLOW, options);

            if (response.getResponse().getData().size() == 0) {
                return null;
            }
            return response.getResponse().getData().get(0);
        }
    }

}
