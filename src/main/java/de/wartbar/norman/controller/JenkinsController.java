package de.wartbar.norman.controller;

import de.wartbar.norman.model.jenkins.JenkinsClientStatus;
import lombok.extern.slf4j.Slf4j;
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

    @RequestMapping(value="/hasstatuschanged", method = RequestMethod.POST)
    public Boolean postHasStatusChanged(@RequestParam Map<String,String> body) throws IOException {
        return JenkinsClientStatus.hasStatusChanged();
    }

    @RequestMapping(value="/getstatus", method = RequestMethod.POST)
    public String post(@RequestParam Map<String,String> body) throws IOException {
        return JenkinsClientStatus.getStatus();
    }

    @RequestMapping(value="/jenkinsnodestoggleoffline", method = RequestMethod.POST)
    public ModelAndView postJenkinsNodesToggleOffline(@RequestParam Map<String,String> body) throws Exception {
        JenkinsClientStatus.toggleOffline(body);
        return getJenkinsNodes();
    }

    @RequestMapping(value="/jenkinsnodes", method = RequestMethod.GET)
    public ModelAndView getJenkinsNodes() throws IOException {
        JenkinsClientStatus.updateStatus();
        ModelAndView modelAndView = new ModelAndView("jenkinsnodes");
        modelAndView.addObject("nodeList", JenkinsClientStatus.getJenkinsClientNodes());
        return modelAndView;
    }




}
