package net.twasi.twitchapi;

import net.twasi.twitchapi.auth.AuthenticationType;
import net.twasi.twitchapi.auth.AuthorizationContext;
import net.twasi.twitchapi.auth.PersonalAuthorizationContext;

public class Constants {

    public static final AuthorizationContext AUTH_CONTEXT = new AuthorizationContext("clientId", "clientSecret", "redirectUri");
    public static final PersonalAuthorizationContext PERSONAL_AUTH_CONTEXT = new PersonalAuthorizationContext("accessToken", "refreshToken", AuthenticationType.OAUTH);

}
