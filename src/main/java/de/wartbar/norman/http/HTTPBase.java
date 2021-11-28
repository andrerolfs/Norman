package de.wartbar.norman.http;

import okhttp3.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HTTPBase {

    //static final OkHttpClient client = new OkHttpClient();

    public static Request buildPostRequest(String url, RequestBody body) {
        return buildPostRequest(url, body, new HashMap<>());
    }

    /*
    FYI : addQueryParameter found here :

    https://www.javaguides.net/2019/05/okhttp-get-request-java-example.html

    For other kind of headers use something like

    Headers headerbuild = Headers.of(header);
    Request request = new Request.Builder().url(url).headers(headerbuild).build();

    found here :

    https://stackoverflow.com/questions/32196424/how-to-add-headers-to-okhttp-request-interceptor
     */
    public static Request.Builder buildRequestBuilder(String url, Map<String, String> queryParameters) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        for (String key : queryParameters.keySet()) {
            urlBuilder.addQueryParameter(key, queryParameters.get(key));
        }
        String urlBuild = urlBuilder.build().toString();

        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(urlBuild);
        return requestBuilder;
    }

    public static Request buildPostRequest(String url, RequestBody body, Map<String, String> header) {
        Request.Builder requestBuilder = buildRequestBuilder(url, header);
        requestBuilder.post(body);
        return requestBuilder.build();
    }

    public static Request buildGetRequest(String url) {
        Request.Builder requestBuilder = buildRequestBuilder(url, new HashMap<>());
        return requestBuilder.build();
    }

    public static Request buildGetRequest(String url, Map<String, String> header) {
        Request.Builder requestBuilder = buildRequestBuilder(url, header);
        return requestBuilder.build();
    }

    public static HTTPResponse executeRequest(Request request) throws IOException {
        return executeRequest(request, null);
    }

    public static HTTPResponse executeRequest(Request request, HTTPCredentials credentials) throws IOException {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (credentials != null) {
            builder.addInterceptor(new BasicAuthInterceptor(credentials.username, credentials.password));
        }
        OkHttpClient client = builder.build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            HTTPResponse httpResponse = new HTTPResponse();

            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                httpResponse.headers.put(responseHeaders.name(i), responseHeaders.value(i));
            }

            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                httpResponse.body = responseBody.string();
            }

            return httpResponse;
        }
    }
}
