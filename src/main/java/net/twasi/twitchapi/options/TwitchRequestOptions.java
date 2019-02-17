package net.twasi.twitchapi.options;

import net.twasi.twitchapi.auth.PersonalAuthorizationContext;

public class TwitchRequestOptions {

    private PersonalAuthorizationContext personalCtx;

    public TwitchRequestOptions withAuth(PersonalAuthorizationContext personalCtx) {
        this.personalCtx = personalCtx;
        return this;
    }

    public PersonalAuthorizationContext getPersonalCtx() {
        return personalCtx;
    }

}
