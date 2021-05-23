package http;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.wartbar.norman.http.HTTPCredentials;
import de.wartbar.norman.http.HTTPGet;
import de.wartbar.norman.http.HTTPResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HTTPGetTest {

    @BeforeEach
    public void setUp() throws Exception {
    }

    //http://localhost:8090/job/ajob/1/api/json?pretty=true
    @Test
    @DisplayName("Test get http://localhost:8090/job/ajob/1/api/json?pretty=true")
    public void testGet_ajob() throws IOException {
        HTTPGet getRequest = new HTTPGet();

        Map<String, String> header = new HashMap<>();
        header.put("pretty", "true");

        HTTPCredentials credentials = new HTTPCredentials();
        credentials.username = "executor";
        credentials.password = "117adab5d57c922170e2d68f6c3f75c91a";

        HTTPResponse response = getRequest.synchronuos("http://localhost:8090/job/ajob/1/api/json", header, credentials);

        assert response.body != null;
        assert response.body.contains("ajob");
    }

}
