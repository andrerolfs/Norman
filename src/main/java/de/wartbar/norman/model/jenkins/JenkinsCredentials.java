package de.wartbar.norman.model.jenkins;

import de.wartbar.norman.http.HTTPCredentials;

public class JenkinsCredentials {

    public static HTTPCredentials credentials = null;

    public static HTTPCredentials get() {
        if (credentials == null) {
            credentials = new HTTPCredentials();
            credentials.username = "executor";
            credentials.password = "117adab5d57c922170e2d68f6c3f75c91a";
        }
        return credentials;
    }
}
