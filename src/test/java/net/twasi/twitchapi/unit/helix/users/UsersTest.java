package net.twasi.twitchapi.unit.helix.users;

import net.twasi.twitchapi.Constants;
import net.twasi.twitchapi.auth.AuthorizationContext;
import net.twasi.twitchapi.auth.PersonalAuthorizationContext;
import net.twasi.twitchapi.helix.users.Users;
import net.twasi.twitchapi.helix.users.response.*;
import net.twasi.twitchapi.mock.MockClient;
import net.twasi.twitchapi.requests.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UsersTest {

    @Test
    public void returnsCurrentUser() {
        AuthorizationContext ctx = Constants.AUTH_CONTEXT;
        PersonalAuthorizationContext personalCtx = Constants.PERSONAL_AUTH_CONTEXT;

        RestClient mocked = new MockClient("helix/users/getCurrent");

        Users users = new Users(mocked, ctx);

        UserDTO user = users.withAuth(personalCtx).getCurrentUser();

        Assertions.assertEquals("1337", user.getId());
        Assertions.assertEquals("loginName", user.getLogin());
        Assertions.assertEquals("displayName", user.getDisplayName());
        Assertions.assertEquals("type", user.getType());
        Assertions.assertEquals("broadcasterType", user.getBroadcasterType());
        Assertions.assertEquals("description", user.getDescription());
        Assertions.assertEquals("profileImageUrl", user.getProfileImageUrl());
        Assertions.assertEquals("offlineImageUrl", user.getOfflineImageUrl());
        Assertions.assertEquals(1337, user.getViewCount());
        Assertions.assertEquals("login@provider.com", user.getEmail());
    }

    @Test
    public void followedBy() {
        AuthorizationContext ctx = Constants.AUTH_CONTEXT;
        PersonalAuthorizationContext personalCtx = Constants.PERSONAL_AUTH_CONTEXT;

        RestClient mocked = new MockClient("helix/users/getFollows");

        Users users = new Users(mocked, ctx);

        UserFollowDTO userFollow = users.withAuth(personalCtx).getFollowedBy("123123");

        Assertions.assertEquals("7777777", userFollow.getFromId());
        Assertions.assertEquals("TwasiBot", userFollow.getFromName());
        Assertions.assertEquals("7777776", userFollow.getToId());
        Assertions.assertEquals("Spendendose", userFollow.getToName());

    }

}
