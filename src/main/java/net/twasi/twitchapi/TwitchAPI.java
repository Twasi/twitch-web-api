package net.twasi.twitchapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import net.twasi.twitchapi.auth.ApiCredentials;
import net.twasi.twitchapi.logging.LoggingConfigurator;
import net.twasi.twitchapi.requests.DefaultRestClient;
import net.twasi.twitchapi.tmi.Tmi;

import java.io.IOException;

public class TwitchAPI {
    private ApiCredentials credentials;
    private static LoggingConfigurator configurator = new LoggingConfigurator();

    public static void setup() {
        configurator.configure();

        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
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

    }

    // Static instances
    private static Tmi tmi = new Tmi(new DefaultRestClient());

    public TwitchAPI(ApiCredentials credentials) {
        this.credentials = credentials;
    }

    public ApiCredentials getCredentials() {
        return this.credentials;
    }

    public Tmi tmi() {
        return tmi;
    }

    public static class Builder {
        private ApiCredentials credentials;

        public Builder() {
        }

        public Builder usingCredentials(ApiCredentials credentials) {
            this.credentials = credentials;
            return this;
        }

        public TwitchAPI build() {
            /* if (credentials == null) {
                throw new RuntimeException("Credentials must be provided before building.");
            }*/

            return new TwitchAPI(credentials);
        }
    }
}
