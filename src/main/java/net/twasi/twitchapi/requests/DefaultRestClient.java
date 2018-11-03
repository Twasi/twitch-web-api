package net.twasi.twitchapi.requests;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import net.twasi.twitchapi.exception.RequestException;

public class DefaultRestClient implements RestClient {

    @Override
    public <T> RestClientResponse<T> get(Class clazz, String url) throws RequestException {

        GetRequest request = Unirest.get(url);

        try {
            HttpResponse<T> result = request.asObject(clazz);

            RestClientResponse<T> response = new RestClientResponse<>(result.getBody());

            return response;
        } catch (UnirestException e) {
            throw new RequestException(e);
        }
    }

}
