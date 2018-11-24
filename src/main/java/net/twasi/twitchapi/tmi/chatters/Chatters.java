package net.twasi.twitchapi.tmi.chatters;

import net.twasi.twitchapi.config.Constants;
import net.twasi.twitchapi.requests.RestClient;
import net.twasi.twitchapi.requests.RestClientResponse;
import net.twasi.twitchapi.tmi.chatters.response.ChattersDTO;

public class Chatters {
    private RestClient client;

    public Chatters(RestClient client) {
        this.client = client;
    }

    public ChattersDTO getByName(String channelName) {
        RestClientResponse<ChattersDTO> response = client.get(ChattersDTO.class, String.format(Constants.TMI_CHATTERS_URL, channelName));

        return response.getResponse();
    }

}
