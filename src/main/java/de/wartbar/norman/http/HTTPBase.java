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

    public static Request.Builder buildRequestBuilder(String url, Map<String, String> header) {
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url);
        for (String key : header.keySet()) {
            requestBuilder.addHeader(key, header.get(key));
        }
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
