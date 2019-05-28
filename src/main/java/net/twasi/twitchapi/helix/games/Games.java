package net.twasi.twitchapi.helix.games;

import net.twasi.twitchapi.auth.AuthorizationContext;
import net.twasi.twitchapi.config.Constants;
import net.twasi.twitchapi.helix.HelixResponseWrapper;
import net.twasi.twitchapi.helix.games.response.GameDTO;
import net.twasi.twitchapi.options.TwitchRequestOptions;
import net.twasi.twitchapi.requests.RequestOptions;
import net.twasi.twitchapi.requests.RestClient;
import net.twasi.twitchapi.requests.RestClientResponse;

import java.util.ArrayList;
import java.util.List;

public class Games {
    private RestClient client;
    private AuthorizationContext ctx;

    public Games(RestClient client, AuthorizationContext ctx) {
        this.client = client;
        this.ctx = ctx;
    }

    public List<GameDTO> getGames(String[] ids, String[] names, TwitchRequestOptions requestOptions) {
        if (ids == null) {
            ids = new String[0];
        }

        if (names == null) {
            names = new String[0];
        }

        if (ids.length == 0 && names.length == 0) {
            return new ArrayList<>();
        }

        if (ids.length + names.length > 100) {
            throw new RuntimeException("You may not supply more than 100 ids/names in total to getGames. Maybe this will be implemented in the future.");
        }

        RequestOptions options = new RequestOptions()
                .withClientId(ctx)
                .withRequestOptions(requestOptions);

        for(String id : ids) {
            options.withQueryString("id", id);
        }

        for(String name : names) {
            options.withQueryString("name", name);
        }

        RestClientResponse<HelixResponseWrapper<GameDTO>> response = client.get(GameDTO.WrappedUserDTO.class, Constants.HELIX_GAMES, options);

        return response.getResponse().getData();
    }
}
