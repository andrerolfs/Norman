package de.wartbar.norman.controller;

import de.wartbar.norman.data.DataBundle;
import de.wartbar.norman.data.DataPreparator;
import de.wartbar.norman.spring.data.persistence.DataBase;
import de.wartbar.norman.spring.data.persistence.EntityModel;
import de.wartbar.norman.spring.data.persistence.EntityModelComparator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class Index {

    static boolean initialized = false;

    @Autowired
    DataBase db;

    void initialize() {
        db.initialize();
        initialized = true;
    }

    @RequestMapping(value="/", method = RequestMethod.POST)
    public void post(@RequestParam Map<String,String> body) {
        if (!initialized) {
            initialize();
        }

        db.add(body);
    }

    @RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView index(@RequestParam Map<String,String> body) {
        if (!initialized) {
            initialize();
        }

        DataBundle bundle = DataPreparator.getList(body, db);

        ModelAndView modelAndView = new ModelAndView("tableview");
        modelAndView.addObject("entities", bundle.list);
        modelAndView.addObject("columns", bundle.keyCounter);
        return modelAndView;
    }

    @RequestMapping(value="/latestset", method = RequestMethod.GET)
    public ModelAndView indexLatestSet(@RequestParam Map<String,String> body) {
        if (!initialized) {
            initialize();
        }

        DataBundle bundle = DataPreparator.getLatestSetList(body, db);

        ModelAndView modelAndView = new ModelAndView("tableview");
        modelAndView.addObject("entities", bundle.list);
        modelAndView.addObject("columns", bundle.keyCounter);
        return modelAndView;
    }
}
