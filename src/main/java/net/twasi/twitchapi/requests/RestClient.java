package net.twasi.twitchapi.requests;

import net.twasi.twitchapi.exception.RequestException;

public interface RestClient {

    public <T> RestClientResponse<T> get(Class clazz, String url) throws RequestException;

    public <T> RestClientResponse<T> post(Class clazz, String url) throws RequestException;

}
