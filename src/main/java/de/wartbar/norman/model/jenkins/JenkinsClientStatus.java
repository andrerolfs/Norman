package de.wartbar.norman.model.jenkins;

import com.google.gson.JsonObject;
import de.wartbar.norman.http.HTTPGet;
import de.wartbar.norman.http.HTTPResponse;
import de.wartbar.norman.util.GsonWrapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JenkinsClientStatus {

    private static Map<String, JenkinsClientNode> clientMap = new HashMap<>();

    private static JenkinsClientNode getClientNode(String name) {
        JenkinsClientNode node = clientMap.get(name);

        if (node == null) {
            node = new JenkinsClientNode();
            clientMap.put(name, node);
        }

        return node;
    }

    public static String getStatus() {
        return GsonWrapper.get().toJson(clientMap);
    }

    public static boolean hasStatusChanged() throws IOException {
        HTTPGet getRequest = new HTTPGet();

        String url = "http://localhost:8090/computer/api/json";
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("pretty", "true");
        queryParameters.put("tree", "computer[displayName,offline]");

        HTTPResponse response = getRequest.synchronuos(url, queryParameters);

        JsonObject jobj = GsonWrapper.get(response.body);
        List<JsonObject> list = GsonWrapper.getAsArray(jobj,"computer");
        for (JsonObject object : list) {
            String name = GsonWrapper.getAsString(object, "displayName");
            boolean online = !GsonWrapper.getAsBoolean(object,"offline");
            JenkinsClientNode node = getClientNode(name);
            node.wasOnline = node.isOnline;
            node.isOnline = online;
        }

        for (JenkinsClientNode node : clientMap.values()) {
            if (node.isOnline != node.wasOnline) {
                return true;
            }
        }

        return false;
    }
}
