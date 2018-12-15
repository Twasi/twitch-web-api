package net.twasi.twitchapi.requests;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import net.twasi.twitchapi.exception.RejectionReason;
import net.twasi.twitchapi.exception.RejectionSolveMethod;

import java.util.logging.Logger;

public class DefaultRestClient implements RestClient {

    private String type = "twasi.twitchapi.restclient";
    private Logger logger = Logger.getLogger(type);

    @Override
    public <T> RestClientResponse<T> get(Class clazz, String url) {
        return get(clazz, url, new RequestOptions());
    }

    @Override
    public <T> RestClientResponse<T> get(Class clazz, String url, RequestOptions options) {

        GetRequest request = Unirest.get(url);

        options.apply(request);

        return execute(request, clazz, options);
    }

    @Override
    public <T> RestClientResponse<T> post(Class clazz, String url) {
        return post(clazz, url, new RequestOptions());
    }

    @Override
    public <T> RestClientResponse<T> post(Class clazz, String url, RequestOptions options) {

        HttpRequestWithBody request = Unirest.post(url);

        return execute(request, clazz, options);
    }

    private <T> RestClientResponse<T> execute(HttpRequest request, Class clazz, RequestOptions options) {
        try {
            HttpResponse<T> result = request.asObject(clazz);

            if (result.getStatus() >= 200 && result.getStatus() <= 299) {
                // Request succeeded!
                RestClientResponse<T> response = new RestClientResponse<>(result.getBody());
                return response;
            }

            if (result.getStatus() == 401 || result.getStatus() == 403) {
                // Authorization problem
                RejectionSolveMethod method = options.handleRejection(RejectionReason.UNAUTHORIZED);

                return handleRejection(request, clazz, options, method, "Authorization failed. Status code: " + result.getStatus());
            }

            // Server problem - request problem
            RejectionSolveMethod method = options.handleRejection(RejectionReason.FAILED);

            return handleRejection(request, clazz, options, method, "Request failed. Status code: " + result.getStatus());

        } catch (UnirestException e) {
            // Connection problem
            RejectionSolveMethod method = options.handleRejection(RejectionReason.FAILED);

            return handleRejection(request, clazz, options, method, "Request failed. UnirestException: " + e.getMessage());
        }
    }

    private <T> RestClientResponse<T> handleRejection(HttpRequest request, Class clazz, RequestOptions options, RejectionSolveMethod method, String information) {
        if (method == RejectionSolveMethod.RETRY) {
            return execute(request, clazz, options);
        }
        if (method == RejectionSolveMethod.FAIL) {
            // Log
            logger.severe("Request failed. URL: " + request.getUrl() + ", " + information);
            return null;
        }
        return null;
    }

}
