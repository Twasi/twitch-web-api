package net.twasi.twitchapi.requests;

public class RestClientResponse<T> {
    private T response;

    public RestClientResponse(T response) {
        this.response = response;
    }

    public T getResponse() {
        return response;
    }

}
