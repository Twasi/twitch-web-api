package net.twasi.twitchapi.helix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HelixResponseWrapper<T> {

    private List<T> data;
    private int total;
    public Map<String, Object> pagination;

    public List<T> getData() {
        return data;
    }

    public int getTotal() {
        return total;
    }

}
