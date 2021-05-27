package json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Slf4j
public class JsonTest {

    @Test
    @DisplayName("Test this code manages to break up circle dependencies when serializing o JSON")
    public void testCircleDependency() throws IOException {
        A a = new A();
        B b = new B();
        a.listOfB.add(b);
        b.aContainingMe = a;

        // when using Expose annotation, you must use GsonBuilder
        // to be able to call excludeFieldsWithoutExposeAnnotation()
        // to enable usage of Expose annotation
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        Gson gson = builder.create();

        String jsonA = gson.toJson(a);

        System.out.println(jsonA);
        log.info(jsonA);
        assert jsonA.equals("{\"name\":\"A\"}");

        String jsonB = gson.toJson(b);
        log.info(jsonB);
        assert  jsonB.equals("{\"name\":\"B\"}");

    }
}