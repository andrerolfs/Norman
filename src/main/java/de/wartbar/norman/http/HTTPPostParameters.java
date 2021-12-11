package de.wartbar.norman.http;

import okhttp3.*;
import java.util.Map;

public class HTTPPostParameters extends HTTPBase {

    public HTTPResponse synchronuos(String url, Map<String, String> queryParameters, HTTPCredentials credentials) throws Exception {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : queryParameters.keySet()) {
            builder.add(key, queryParameters.get(key));
        }
        RequestBody body = builder.build();

        return executeRequest(buildPostRequest(url,body), credentials);
    }
}
