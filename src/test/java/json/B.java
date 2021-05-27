package json;

import com.google.gson.annotations.Expose;

public class B {

    // when using Expose annotation, all members not annotated with Expose will be ignored
    @Expose public String name = "B";
    public A aContainingMe;
}
