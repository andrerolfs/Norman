package de.wartbar.norman.model.jenkins;

import com.google.gson.annotations.Expose;
import de.wartbar.norman.data.ModelDefaults;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JenkinsClientNode extends ModelDefaults {

    @Expose public Boolean wasOnline = false;
    @Expose public Boolean isOnline = false;
    @Expose public String name = "";

}
