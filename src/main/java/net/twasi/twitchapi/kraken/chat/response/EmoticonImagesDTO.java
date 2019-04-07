package net.twasi.twitchapi.kraken.chat.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class EmoticonImagesDTO {
    @JsonProperty("emoticon_sets")
    private Map<String, List<EmoticonDTO>> emoticonSets;

    public Map<String, List<EmoticonDTO>> getEmoticonSets() {
        return emoticonSets;
    }
}
