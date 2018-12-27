package net.twasi.twitchapi.kraken.channels;

import net.twasi.twitchapi.auth.AuthorizationContext;
import net.twasi.twitchapi.auth.PersonalAuthorizationContext;
import net.twasi.twitchapi.config.Constants;
import net.twasi.twitchapi.kraken.channels.response.ChannelDTO;
import net.twasi.twitchapi.requests.RequestOptions;
import net.twasi.twitchapi.requests.RestClient;
import net.twasi.twitchapi.requests.RestClientResponse;
import org.json.JSONObject;

public class Channels {
    private RestClient client;
    private AuthorizationContext ctx;

    public Channels(RestClient client, AuthorizationContext ctx) {
        this.client = client;
        this.ctx = ctx;
    }

    public UsersWithAuth withAuth(PersonalAuthorizationContext personalCtx) {
        return new UsersWithAuth(personalCtx);
    }

    public class UsersWithAuth {
        private PersonalAuthorizationContext personalCtx;

        UsersWithAuth(PersonalAuthorizationContext personalCtx) {
            this.personalCtx = personalCtx;
        }

        public ChannelDTO updateChannel(String status, String game) {
            JSONObject obj = new JSONObject();
            JSONObject channel = new JSONObject();

            if (status != null) {
                channel.put("status", status);
            }

            if (game != null) {
                channel.put("game", game);
            }
            obj.put("channel", channel);

            String json = obj.toString();

            RequestOptions options = new RequestOptions()
                    .withPersonalAuth(personalCtx)
                    .withV5()
                    .withClientId(ctx)
                    .withJsonBody(json);

            RestClientResponse<ChannelDTO> response = client.put(ChannelDTO.class, Constants.KRAKEN_CHANNELS + "/" + personalCtx.getTwitchId(), options);

            return response.getResponse();
        }
    }
}
