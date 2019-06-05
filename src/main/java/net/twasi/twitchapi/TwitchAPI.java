package net.twasi.twitchapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import net.twasi.twitchapi.auth.AuthorizationContext;
import net.twasi.twitchapi.exception.NotInitializedException;
import net.twasi.twitchapi.helix.Helix;
import net.twasi.twitchapi.id.oauth2.Authentication;
import net.twasi.twitchapi.kraken.Kraken;
import net.twasi.twitchapi.logging.LoggingConfigurator;
import net.twasi.twitchapi.requests.DefaultRestClient;
import net.twasi.twitchapi.requests.RestClient;
import net.twasi.twitchapi.tmi.Tmi;

import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class TwitchAPI {

    private static boolean isInitialized = false;
    private static Logger logger = LoggingConfigurator.getLogger("TwitchAPI");

    private static AuthorizationContext authContext;
    private static RestClient client;

    // Authentication (OAuth, Credentials)
    private static Authentication authentication;

    // Tmi (Chatters)
    private static Tmi tmi;

    // Helix (new Api)
    private static Helix helix;

    // Kraken (v5 Api)
    private static Kraken kraken;

    public static void initialize(AuthorizationContext authContext) {
        TwitchAPI.authContext = authContext;
        TwitchAPI.client = new DefaultRestClient();

        TwitchAPI.authentication = new Authentication(TwitchAPI.client, TwitchAPI.authContext);

        TwitchAPI.tmi = new Tmi(TwitchAPI.client);

        TwitchAPI.helix = new Helix(TwitchAPI.client, TwitchAPI.authContext);

        TwitchAPI.kraken = new Kraken(TwitchAPI.client, TwitchAPI.authContext);

        // Setup unirest
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    // We should not throw an exception here since this would prevent us to handle status codes,
                    // because the exception is raised before status code assertion is done. Error messages have
                    // another format than the expected, so that happens quite often.
                    //throw new RuntimeException(e);
                    logger.info("Exception was thrown while requesting: " + e.getMessage());
                    return null;
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });

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

    public static Kraken kraken() {
        checkInitialization();
        return kraken;
    }

}
