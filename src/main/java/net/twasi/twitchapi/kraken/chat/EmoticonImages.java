package net.twasi.twitchapi.kraken.chat;

import net.twasi.twitchapi.auth.AuthorizationContext;
import net.twasi.twitchapi.config.Constants;
import net.twasi.twitchapi.kraken.chat.response.EmoticonImagesDTO;
import net.twasi.twitchapi.requests.RequestOptions;
import net.twasi.twitchapi.requests.RestClient;
import net.twasi.twitchapi.requests.RestClientResponse;

public class EmoticonImages {
    private RestClient client;
    private AuthorizationContext ctx;

    public EmoticonImages(RestClient client, AuthorizationContext ctx) {
        this.client = client;
        this.ctx = ctx;
    }

    public EmoticonImagesDTO getEmoticonImages(String emotesets) {
        RequestOptions options = new RequestOptions()
                .withV5()
                .withClientId(ctx)
                .withQueryString("emotesets", emotesets);

        RestClientResponse<EmoticonImagesDTO> response = client.get(EmoticonImagesDTO.class, Constants.KRAKEN_EMOTICON_IMAGES, options);

        return response.getResponse();
    }

    /* public EmoticonImagesWithAuth withAuth(PersonalAuthorizationContext personalCtx) {
        return new EmoticonImagesWithAuth(personalCtx);
    }

    public class EmoticonImagesWithAuth {
        private PersonalAuthorizationContext personalCtx;

        EmoticonImagesWithAuth(PersonalAuthorizationContext personalCtx) {
            this.personalCtx = personalCtx;
        }


    } */
}
