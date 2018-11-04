package net.twasi.twitchapi.tmi.chatters;

import net.twasi.twitchapi.config.Constants;
import net.twasi.twitchapi.exception.RequestException;
import net.twasi.twitchapi.requests.RestClient;
import net.twasi.twitchapi.requests.RestClientResponse;
import net.twasi.twitchapi.tmi.chatters.response.ChattersDTO;

import java.util.logging.Logger;

public class Chatters {
    private String type = "twasi.twitchapi.tmi.chatters";
    private Logger logger = Logger.getLogger(type);
    private RestClient client;

    public Chatters(RestClient client) {
        this.client = client;
    }

    public ChattersDTO getByName(String channelName) {
        try {
            RestClientResponse<ChattersDTO> response = client.get(ChattersDTO.class, String.format(Constants.TMI_CHATTERS_URL, channelName));

            return response.getResponse();
        } catch (RequestException e) {
            logger.severe(String.format("Request %s/getByName failed for channel %s: %s", type, channelName, e.getMessage()));
            return null;
        }
    }

}
