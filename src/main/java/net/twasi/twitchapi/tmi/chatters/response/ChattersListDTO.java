package net.twasi.twitchapi.tmi.chatters.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ChattersListDTO {

    private List<String> vips = new ArrayList<>();
    private List<String> moderators = new ArrayList<>();
    private List<String> staff = new ArrayList<>();
    private List<String> admins = new ArrayList<>();
    @JsonProperty("global_mods")
    private List<String> globalMods = new ArrayList<>();
    private List<String> viewers = new ArrayList<>();

    public List<String> getVips() {
        return vips;
    }

    public List<String> getModerators() {
        return moderators;
    }

    public List<String> getStaff() {
        return staff;
    }

    public List<String> getAdmins() {
        return admins;
    }

    public List<String> getGlobalMods() {
        return globalMods;
    }

    public List<String> getViewers() {
        return viewers;
    }

    public List<String> getAll() {
        List<String> all = new ArrayList<>();

        all.addAll(getVips());
        all.addAll(getModerators());
        all.addAll(getStaff());
        all.addAll(getAdmins());
        all.addAll(getGlobalMods());
        all.addAll(getViewers());

        return all;
    }
}
