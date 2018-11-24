package net.twasi.twitchapi.helix;

import net.twasi.twitchapi.auth.AuthorizationContext;
import net.twasi.twitchapi.helix.users.Users;
import net.twasi.twitchapi.requests.RestClient;

public class Helix {
    private Users users;

    private RestClient client;
    private AuthorizationContext ctx;

    public Helix(RestClient client, AuthorizationContext ctx) {
        this.client = client;
        this.ctx = ctx;

        users = new Users(client, ctx);
    }

    public Users users() {
        return users;
    }
}
