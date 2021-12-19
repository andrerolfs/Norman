package de.wartbar.norman.controller;

import de.wartbar.norman.data.WebDefaults;
import de.wartbar.norman.model.jenkins.JenkinsClientStatus;
import de.wartbar.norman.model.jenkins.JenkinsCredentials;
import de.wartbar.norman.spring.data.persistence.DataBase;
import de.wartbar.norman.spring.data.persistence.JenkinsConfigModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
public class JenkinsController {

    @Autowired
    DataBase db;

    @RequestMapping(value="/hasstatuschanged", method = RequestMethod.POST)
    public Boolean postHasStatusChanged(@RequestParam Map<String,String> body) throws IOException {
        return JenkinsClientStatus.hasStatusChanged();
    }

    @RequestMapping(value="/getstatus", method = RequestMethod.POST)
    public String post(@RequestParam Map<String,String> body) throws IOException {
        return JenkinsClientStatus.getStatus();
    }

    @RequestMapping(value="/jenkinsnodes", method = RequestMethod.POST)
    public ModelAndView postJenkinsNodesToggleOffline(@RequestParam Map<String,String> body) throws Exception {
        JenkinsClientStatus.toggleOffline(body);
        return getJenkinsNodes();
    }

    @RequestMapping(value="/jenkinsnodes", method = RequestMethod.GET)
    public ModelAndView getJenkinsNodes() throws IOException {

        if (JenkinsCredentials.get() == null) {
            JenkinsConfigModel jenkinsConfigModel = db.getJenkinsConfigModel();
            if (jenkinsConfigModel != null) {
                JenkinsCredentials.update(jenkinsConfigModel);
            } else {
                ModelAndView modelAndView = WebDefaults.createModelAndView(db, "jenkinsnodesproblem");
                return modelAndView;
            }
        }

        JenkinsClientStatus.updateStatus();
        ModelAndView modelAndView = WebDefaults.createModelAndView(db, "jenkinsnodes");
        modelAndView.addObject("nodeList", JenkinsClientStatus.getJenkinsClientNodes());
        return modelAndView;
    }

    @RequestMapping(value="/jenkinsconfig", method = RequestMethod.POST)
    public ModelAndView postJenkinsConfig(@RequestParam Map<String,String> body) throws Exception {
        db.saveJenkinsConfigModel(body);
        JenkinsCredentials.update(db.getJenkinsConfigModel());
        WebDefaults.updateName(db.getJenkinsConfigModel().getName());
        return getJenkinsConfig();
    }

    @RequestMapping(value="/jenkinsconfig", method = RequestMethod.GET)
    public ModelAndView getJenkinsConfig() throws IOException {
        JenkinsConfigModel jenkinsConfigModel = db.getJenkinsConfigModel();
        if (jenkinsConfigModel == null) {
            jenkinsConfigModel = new JenkinsConfigModel();
        }
        ModelAndView modelAndView = WebDefaults.createModelAndView(db, "jenkinsconfig");
        modelAndView.addObject("config", jenkinsConfigModel);
        return modelAndView;
    }
}
