package de.wartbar.norman.http;

import okhttp3.*;
import java.util.Map;

public class HTTPPostParameters extends HTTPBase {

    public HTTPResponse synchronous(Map<String, String> parameters, String url) throws Exception {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : parameters.keySet()) {
            builder.add(key, parameters.get(key));
        }
        RequestBody body = builder.build();

        return executeRequest(buildPostRequest(url,body));
    }
}
