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

    // GET
    @Override
    public <T> RestClientResponse<T> get(Class clazz, String url) {
        return get(clazz, url, new RequestOptions());
    }

    @Override
    public <T> RestClientResponse<T> get(Class clazz, String url, RequestOptions options) {

        GetRequest request = Unirest.get(url);

        return execute(request, clazz, options);
    }

    // POST
    @Override
    public <T> RestClientResponse<T> post(Class clazz, String url) {
        return post(clazz, url, new RequestOptions());
    }

    @Override
    public <T> RestClientResponse<T> post(Class clazz, String url, RequestOptions options) {

        HttpRequestWithBody request = Unirest.post(url);

        return execute(request, clazz, options);
    }

    // PUT
    @Override
    public <T> RestClientResponse<T> put(Class clazz, String url) {
        return put(clazz, url, new RequestOptions());
    }

    @Override
    public <T> RestClientResponse<T> put(Class clazz, String url, RequestOptions options) {

        HttpRequestWithBody request = Unirest.put(url);

        return execute(request, clazz, options);
    }


    private <T> RestClientResponse<T> execute(HttpRequest request, Class clazz, RequestOptions options) {
        options.apply(request);

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

                logger.info("Returned unauthorized / no access. Trying to refresh oauth token.");

                return handleRejection(request, clazz, options, method, "Authorization failed. Status code: " + result.getStatus() + ", Body: " + convertStreamToString(result.getRawBody()));
            }

            // Server problem - request problem
            RejectionSolveMethod method = options.handleRejection(RejectionReason.FAILED);

            return handleRejection(request, clazz, options, method, "Request failed. Status code: " + result.getStatus() + ", Body: " + convertStreamToString(result.getRawBody()));

        } catch (UnirestException e) {
            // Connection problem
            RejectionSolveMethod method = options.handleRejection(RejectionReason.FAILED);

            return handleRejection(request, clazz, options, method, "Request failed. UnirestException: " + e.getMessage());
        }
    }

    private <T> RestClientResponse<T> handleRejection(HttpRequest request, Class clazz, RequestOptions options, RejectionSolveMethod method, String information) {
        if (method == RejectionSolveMethod.RETRY) {
            // Clear all headers (they will be reset)
            request.getHeaders().clear();

            return execute(request, clazz, options);
        }
        if (method == RejectionSolveMethod.FAIL) {
            // Log
            logger.severe("Request failed. URL: " + request.getUrl() + ", " + information);
            logger.severe("Headers sent: " + request.getHeaders());
            logger.severe("HTTP method: " + request.getHttpMethod());
            logger.severe("HTTP URL: " + request.getUrl());
            logger.severe("Retry attempts: " + options.getRetries());
            return null;
        }
        return null;
    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}
