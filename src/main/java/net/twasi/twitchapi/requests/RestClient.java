package net.twasi.twitchapi.requests;

public interface RestClient {

    <T> RestClientResponse<T> get(Class clazz, String url);
    <T> RestClientResponse<T> get(Class clazz, String url, RequestOptions options);

    <T> RestClientResponse<T> post(Class clazz, String url);
    <T> RestClientResponse<T> post(Class clazz, String url, RequestOptions options);

}
