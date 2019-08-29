package net.twasi.twitchapi.requests;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import net.twasi.twitchapi.exception.RejectionReason;
import net.twasi.twitchapi.exception.RejectionSolveMethod;
import net.twasi.twitchapi.logging.LoggingConfigurator;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultRestClient implements RestClient {

    private String type = "twasi.twitchapi.restclient";
    private Logger logger = LoggingConfigurator.getLogger(type);

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

                return handleRejection(request, clazz, options, method, "Authorization failed. Status code: " + result.getStatus() + ", Body: " + convertStreamToString(result.getRawBody()), null);
            }

            if (result.getStatus() == 429) {
                // Rate limiting problem

                // Check if there is a token provided
                if (options.getTwitchRequestOptions() != null && options.getTwitchRequestOptions().getPersonalCtx() != null) {
                    // Check if it's valid
                    if (!options.getTwitchRequestOptions().getPersonalCtx().isValid()) {
                        // Yeah it's invalid, we can refresh it.
                        options.getTwitchRequestOptions().getPersonalCtx().autoRefresh();

                        // Retry it.
                        return handleRejection(request, clazz, options, RejectionSolveMethod.RETRY, null, null);
                    }

                    // The token is valid but it failed anyways. Probably hit the hard limit. Fail.
                    return handleRejection(request, clazz, options, RejectionSolveMethod.FAIL, "Rate limiting hit. Auth token provided & valid. Headers sent: " + result.getHeaders(), null);
                }

                // There was no auth token provided. Problematic :O
                return handleRejection(request, clazz, options, RejectionSolveMethod.FAIL, "Rate limiting hit. Auth token NOT provided.\r\n" +
                        "Ratelimit-Limit (requests/minute): " + result.getHeaders().getFirst("Ratelimit-Limit") + "\r\n" +
                        "Ratelimit-Remaining: " + result.getHeaders().getFirst("Ratelimit-Remaining") + "\r\n" +
                        "Ratelimit-Reset: " + result.getHeaders().getFirst("Ratelimit-Reset"), null);
            }

            // Server problem - request problem
            RejectionSolveMethod method = options.handleRejection(RejectionReason.FAILED);

            return handleRejection(request, clazz, options, method, "Request failed. Status code: " + result.getStatus() + ", Body: " + convertStreamToString(result.getRawBody()), null);

        } catch (UnirestException e) {
            // Connection problem
            RejectionSolveMethod method = options.handleRejection(RejectionReason.FAILED);

            return handleRejection(request, clazz, options, method, "Request failed. UnirestException: " + e.getMessage(), e);
        }
    }

    private <T> RestClientResponse<T> handleRejection(HttpRequest request, Class clazz, RequestOptions options, RejectionSolveMethod method, String information, Exception e) {
        if (method == RejectionSolveMethod.RETRY) {
            // Clear all headers (they will be reset)

            if (request instanceof HttpRequestWithBody) {
                request = new HttpRequestWithBody(request.getHttpMethod(), request.getUrl().split("\\?")[0]);
            } else {
                request = new HttpRequest(request.getHttpMethod(), request.getUrl().split("\\?")[0]);
            }

            return execute(request, clazz, options);
        }
        if (method == RejectionSolveMethod.FAIL) {
            if (options.shouldFailSilently()) {
                return null;
            }

            // Log
            logger.severe("Request failed. URL: " + request.getUrl() + ", " + information + "\r\n" +
                    "Headers sent: " + request.getHeaders() + "\r\n" +
                    "HTTP method: " + request.getHttpMethod() + "\r\n" +
                    "HTTP URL: " + request.getUrl() + "\r\n" +
                    "Retry attempts: " + options.getRetries());
            if (e != null) {
                logger.log(Level.INFO, "Exception thrown while requesting", e);
            }
            return null;
        }
        return null;
    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}
