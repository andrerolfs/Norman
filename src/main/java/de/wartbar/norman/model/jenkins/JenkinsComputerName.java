package de.wartbar.norman.model.jenkins;

import de.wartbar.norman.data.Constants;

import java.util.Map;

public class JenkinsComputerName {

    public static String get(Map<String,String> body) {
        String nodeName = body.get(Constants.node);
        if (nodeName.equals(Constants.master)) {
            nodeName = "(" + Constants.master + ")";
        }
        return nodeName;
    }
}
