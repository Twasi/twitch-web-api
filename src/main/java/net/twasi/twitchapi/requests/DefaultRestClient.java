package net.twasi.twitchapi.requests;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import net.twasi.twitchapi.exception.RequestException;

public class DefaultRestClient implements RestClient {

    @Override
    public <T> RestClientResponse<T> get(Class clazz, String url) throws RequestException {

        GetRequest request = Unirest.get(url);

        return execute(request, clazz);
    }

    @Override
    public <T> RestClientResponse<T> get(Class clazz, String url, String authValue) throws RequestException {

        GetRequest request = Unirest.get(url);
        request.header("Authorization", authValue);

        return execute(request, clazz);
    }

    @Override
    public <T> RestClientResponse<T> post(Class clazz, String url) throws RequestException {

        HttpRequestWithBody request = Unirest.post(url);

        return execute(request, clazz);
    }

    private <T> RestClientResponse<T> execute(HttpRequest request, Class clazz) throws RequestException {
        try {
            HttpResponse<T> result = request.asObject(clazz);

            RestClientResponse<T> response = new RestClientResponse<>(result.getBody());

            return response;
        } catch (UnirestException e) {
            throw new RequestException(e);
        }
    }

}
