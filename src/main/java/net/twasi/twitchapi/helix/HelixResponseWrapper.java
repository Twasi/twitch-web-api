package net.twasi.twitchapi.helix;

import java.util.List;

public class HelixResponseWrapper<T> {

    private List<T> data;

    public List<T> getData() {
        return data;
    }

}
