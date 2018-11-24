package net.twasi.twitchapi.helix.users;

import net.twasi.twitchapi.auth.AuthorizationContext;
import net.twasi.twitchapi.auth.PersonalAuthorizationContext;
import net.twasi.twitchapi.config.Constants;
import net.twasi.twitchapi.helix.HelixResponseWrapper;
import net.twasi.twitchapi.requests.RequestOptions;
import net.twasi.twitchapi.requests.RestClient;
import net.twasi.twitchapi.helix.users.response.*;
import net.twasi.twitchapi.requests.RestClientResponse;

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

    public class UsersWithAuth {
        private PersonalAuthorizationContext personalCtx;

        UsersWithAuth(PersonalAuthorizationContext personalCtx) {
            this.personalCtx = personalCtx;
        }

        public UserDTO getCurrentUser() {
            RestClientResponse<HelixResponseWrapper<UserDTO>> response = client.get(UserDTO.WrappedUserDTO.class, Constants.HELIX_USERS, new RequestOptions().withPersonalAuth(personalCtx));

            return response.getResponse().getData().get(0);
        }
    }

}
