package de.wartbar.norman.model.jenkins;

import de.wartbar.norman.http.HTTPCredentials;
import de.wartbar.norman.spring.data.persistence.JenkinsConfigModel;

public class JenkinsCredentials {

    static HTTPCredentials httpCredentials = null;

    public static void update(JenkinsConfigModel jenkinsConfigModel) {
        httpCredentials = new HTTPCredentials();
        httpCredentials.username = jenkinsConfigModel.getUsername();
        httpCredentials.password = jenkinsConfigModel.getPassword();
    }

    public static HTTPCredentials get() {
        return httpCredentials;
    }
}
