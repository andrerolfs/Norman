package de.wartbar.norman.model.jenkins;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JenkinsClientNode {

    @Expose public Boolean wasOnline = false;
    @Expose public Boolean isOnline = false;
    @Expose public String name = "";

    public static String getCOLOR() {
        return "000000";
    }

    public static String getBACKGROUND_COLOR() {
        return "FFFFFF";
    }
}
