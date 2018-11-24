package net.twasi.twitchapi.helix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HelixResponseWrapper<T> {

    private List<T> data;

    public List<T> getData() {
        return data;
    }

}
