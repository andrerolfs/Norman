package de.wartbar.norman.http;

import okhttp3.*;
import java.io.IOException;
import java.util.Map;

public class HTTPPostJson extends HTTPBase {

    public HTTPResponse synchronuos(String inputJson, String url) throws IOException {
        RequestBody body = RequestBody.create(
                inputJson,
                MediaType.parse("application/json; charset=utf-8")
        );

        return executeRequest(buildPostRequest(url,body));
    }

    public HTTPResponse synchronuos(String inputJson, String url, Map<String, String> header) throws IOException {
        RequestBody body = RequestBody.create(
                inputJson,
                MediaType.parse("application/json; charset=utf-8")
        );

        return executeRequest(buildPostRequest(url,body, header));
    }
}
