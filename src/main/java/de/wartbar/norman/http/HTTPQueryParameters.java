package de.wartbar.norman.http;

import de.wartbar.norman.data.Constants;

import java.util.HashMap;
import java.util.Map;

public class HTTPQueryParameters {

    Map<String, String> queryParameters = new HashMap<>();

    private HTTPQueryParameters() {}

    public static HTTPQueryParameters create() {
        return new HTTPQueryParameters();
    }

    public HTTPQueryParameters put(String key, String value) {
        queryParameters.put(key, value);
        return this;
    }

    public Map<String, String> get() {
        return queryParameters;
    }
}
