package de.wartbar.norman.http;

import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HTTPGet extends HTTPBase {

    public HTTPResponse synchronuos(String url, HTTPCredentials credentials) throws IOException {
        Request request = buildGetRequest(url, new HashMap<>());
        return executeRequest(request, credentials);
    }

    public HTTPResponse synchronuos(String url, Map<String, String> header, HTTPCredentials credentials) throws IOException {
        Request request = buildGetRequest(url, header);
        return executeRequest(request, credentials);
    }

    public HTTPResponse synchronuos(String url) throws IOException {
        Request request = buildGetRequest(url, new HashMap<>());
        return executeRequest(request);
    }

    public HTTPResponse synchronuos(String url, Map<String, String> header) throws IOException {
        Request request = buildGetRequest(url, header);
        return executeRequest(request);
    }
}
