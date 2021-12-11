package de.wartbar.norman.model.jenkins;

import com.google.gson.JsonObject;
import de.wartbar.norman.data.Constants;
import de.wartbar.norman.http.*;
import de.wartbar.norman.util.GsonWrapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class JenkinsClientStatus {

    private static Map<String, JenkinsClientNode> clientMap = new HashMap<>();

    public static List<JenkinsClientNode> getJenkinsClientNodes() {
        return new ArrayList<>(clientMap.values());
    }

    private static JenkinsClientNode getClientNode(String name) {
        JenkinsClientNode node = clientMap.get(name);

        if (node == null) {
            node = new JenkinsClientNode();
            node.name = name;
            clientMap.put(name, node);
        }

        return node;
    }

    public static String getStatus() {
        return GsonWrapper.get().toJson(clientMap);
    }

    public static void updateStatus() throws IOException {
        HTTPGet getRequest = new HTTPGet();

        String url = "http://localhost:8090/computer/api/json";
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("pretty", "true");
        queryParameters.put("tree", "computer[displayName,offline]");

        HTTPCredentials credentials = new HTTPCredentials();
        credentials.username = "executor";
        credentials.password = "117adab5d57c922170e2d68f6c3f75c91a";

        HTTPResponse response = getRequest.synchronuos(url, queryParameters, credentials);

        JsonObject jobj = GsonWrapper.get(response.body);
        List<JsonObject> list = GsonWrapper.getAsArray(jobj,"computer");
        for (JsonObject object : list) {
            String name = GsonWrapper.getAsString(object, "displayName");
            boolean online = !GsonWrapper.getAsBoolean(object,"offline");
            JenkinsClientNode node = getClientNode(name);
            node.wasOnline = node.isOnline;
            node.isOnline = online;
        }
    }

    public static boolean hasStatusChanged() throws IOException {

        updateStatus();

        for (JenkinsClientNode node : clientMap.values()) {
            if (node.isOnline != node.wasOnline) {
                return true;
            }
        }

        return false;
    }

    public static void toggleOffline(Map<String,String> body) throws Exception {
        HTTPPostParameters postRequest = new HTTPPostParameters();

        //http://localhost:8090/computer/ClientNode/toggleOffline?offlineMessage=aReason
        String nodeName = body.get(Constants.node);
        if (nodeName.equals(Constants.master)) {
            nodeName = "(" + Constants.master + ")";
        }

        String url = "http://localhost:8090/computer/" + nodeName + "/" + Constants.toggleOffline;
        Map<String, String> queryParameters = new HashMap<>();

        HTTPCredentials credentials = new HTTPCredentials();
        credentials.username = "executor";
        credentials.password = "117adab5d57c922170e2d68f6c3f75c91a";

        HTTPResponse response = postRequest.synchronuos(url, queryParameters, credentials);

    }
}
