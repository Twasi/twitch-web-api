package net.twasi.twitchapi.helix.streams;

import net.twasi.twitchapi.auth.AuthorizationContext;
import net.twasi.twitchapi.config.Constants;
import net.twasi.twitchapi.helix.HelixResponseWrapper;
import net.twasi.twitchapi.helix.streams.response.StreamDTO;
import net.twasi.twitchapi.options.TwitchRequestOptions;
import net.twasi.twitchapi.requests.RequestOptions;
import net.twasi.twitchapi.requests.RestClient;
import net.twasi.twitchapi.requests.RestClientResponse;

public class Streams {

    private RestClient client;
    private AuthorizationContext ctx;

    public Streams(RestClient client, AuthorizationContext ctx) {
        this.client = client;
        this.ctx = ctx;
    }

    public HelixResponseWrapper<StreamDTO> getStreamsByUser(String userId, int limit, TwitchRequestOptions requestOptions) {
        if (limit <= 0) {
            throw new RuntimeException("You need to supply a limit that is greater than 0.");
        }

        RequestOptions options = new RequestOptions()
                .withClientId(ctx)
                .withQueryString("user_id", userId)
                .withQueryString("first", String.valueOf(limit))
                .withRequestOptions(requestOptions);

        RestClientResponse<HelixResponseWrapper<StreamDTO>> streams = client.get(StreamDTO.WrappedStreamDTO.class, Constants.HELIX_STREAMS, options);

        return streams.getResponse();
    }

}
