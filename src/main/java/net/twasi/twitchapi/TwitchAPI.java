package net.twasi.twitchapi;

import net.twasi.twitchapi.auth.AuthorizationContext;
import net.twasi.twitchapi.exception.NotInitializedException;
import net.twasi.twitchapi.helix.Helix;
import net.twasi.twitchapi.id.oauth2.Authentication;
import net.twasi.twitchapi.requests.DefaultRestClient;
import net.twasi.twitchapi.requests.RestClient;
import net.twasi.twitchapi.tmi.Tmi;

public class TwitchAPI {

    private static boolean isInitialized = false;

    private static AuthorizationContext authContext;
    private static RestClient client;

    // Authentication (OAuth, Credentials)
    private static Authentication authentication;

    // Tmi (Chatters)
    private static Tmi tmi;

    // Helix (new Api)
    private static Helix helix;

    public static void initialize(AuthorizationContext authContext) {
        TwitchAPI.authContext = authContext;
        TwitchAPI.client = new DefaultRestClient();

        TwitchAPI.authentication = new Authentication(TwitchAPI.client, TwitchAPI.authContext);

        TwitchAPI.tmi = new Tmi(TwitchAPI.client);

        TwitchAPI.helix = new Helix(TwitchAPI.client, TwitchAPI.authContext);

        TwitchAPI.isInitialized = true;
    }

    private static void checkInitialization() {
        if (!isInitialized) {
            throw new NotInitializedException("TwitchAPI is not initialized.");
        }
    }

    public static Authentication authentication() {
        checkInitialization();
        return authentication;
    }

    public static Tmi tmi() {
        checkInitialization();
        return tmi;
    }

    public static Helix helix() {
        checkInitialization();
        return helix;
    }

}
