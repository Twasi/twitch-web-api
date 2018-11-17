package net.twasi.twitchapi.requests;

import net.twasi.twitchapi.exception.RequestException;

public interface RestClient {

    <T> RestClientResponse<T> get(Class clazz, String url) throws RequestException;
    <T> RestClientResponse<T> get(Class clazz, String url, RequestOptions options) throws RequestException;

    <T> RestClientResponse<T> post(Class clazz, String url) throws RequestException;
    <T> RestClientResponse<T> post(Class clazz, String url, RequestOptions options) throws RequestException;

}
