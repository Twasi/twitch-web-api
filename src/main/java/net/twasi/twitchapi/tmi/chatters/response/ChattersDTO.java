package net.twasi.twitchapi.tmi.chatters.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(value = { "_links" })
public class ChattersDTO {

    @JsonProperty("chatter_count")
    private int chatterCount;
    private ChattersListDTO chatters;

    public int getChatterCount() {
        return chatterCount;
    }

    public ChattersListDTO getChatters() {
        return chatters;
    }
}
