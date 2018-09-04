package net.twasi.twitchapi;

import net.twasi.twitchapi.auth.ApiCredentials;

public class TwitchAPI {
    private ApiCredentials credentials;

    public TwitchAPI(ApiCredentials credentials) {
        this.credentials = credentials;
    }

    class Builder {
        private ApiCredentials credentials;

        public Builder() {
        }

        public Builder usingCredentials(ApiCredentials credentials) {
            this.credentials = credentials;
            return this;
        }

        public TwitchAPI build() {
            if (credentials == null) {
                throw new RuntimeException("Credentials need to be provided before building.");
            }

            return new TwitchAPI(credentials);
        }
    }
}
