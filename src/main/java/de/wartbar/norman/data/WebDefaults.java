package de.wartbar.norman.data;

import de.wartbar.norman.spring.data.persistence.DataBase;
import de.wartbar.norman.spring.data.persistence.JenkinsConfigModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

public class WebDefaults {

    static String name = null;

    public static void updateName(String name) {
        WebDefaults.name = name;
    }

    public static ModelAndView createModelAndView( DataBase db, String viewName) {

        if (name == null) {
            JenkinsConfigModel jcm = db.getJenkinsConfigModel();
            if (jcm == null) {
                name = Constants.Norman;
            } else {
                name = jcm.getName();
            }
        }

        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject("name", name);
        modelAndView.addObject("username", WebDefaults.getUsername());
        return  modelAndView;
    }

    public static String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    public static void logout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.setAuthenticated(false);
    }
}
