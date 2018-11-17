package net.twasi.twitchapi.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.twasi.twitchapi.exception.RequestException;
import net.twasi.twitchapi.requests.RequestOptions;
import net.twasi.twitchapi.requests.RestClient;
import net.twasi.twitchapi.requests.RestClientResponse;

import java.io.*;
import java.net.URL;

public class MockClient implements RestClient {
    String jsonPath;

    public MockClient(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    public File openResource(String path) {
        URL resource = getClass().getClassLoader().getResource(path);

        if (resource == null) {
            throw new RuntimeException("Resource can't be opened: " + path);
        }

        File file = new File(resource.getFile());

        return file;
    }

    @Override
    public <T> RestClientResponse<T> get(Class clazz, String url) throws RequestException {
        String path = jsonPath + "_get.json";

        return getResponse(path, clazz);
    }

    @Override
    public <T> RestClientResponse<T> get(Class clazz, String url, RequestOptions options) throws RequestException {
        String path = jsonPath + "_get.json";

        return getResponse(path, clazz);
    }

    @Override
    public <T> RestClientResponse<T> post(Class clazz, String url) throws RequestException {
        String path = jsonPath + "_post.json";

        return getResponse(path, clazz);
    }

    @Override
    public <T> RestClientResponse<T> post(Class clazz, String url, RequestOptions options) throws RequestException {
        String path = jsonPath + "_post.json";

        return getResponse(path, clazz);
    }

    private <T> RestClientResponse<T> getResponse(String path, Class clazz) {
        ObjectMapper mapper = new ObjectMapper();

        T result = null;
        try {
            result = (T)mapper.readValue(openResource(path), clazz);
        } catch (IOException e) {
            throw new RuntimeException("Error while parsing mock data json: " + path, e);
        }

        return new RestClientResponse(result);
    }

}
