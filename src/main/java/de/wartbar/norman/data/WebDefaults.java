package de.wartbar.norman.data;

import de.wartbar.norman.spring.data.persistence.DataBase;
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
            name = db.getJenkinsConfigModel().getName();
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
}
