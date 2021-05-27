package json;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class A {

    // when using Expose annotation, all members which shall not be ignored, must be exposed
    @Expose public String name = "A";
    @Expose(serialize = false, deserialize = false) public List<B> listOfB = new ArrayList<>();
}
