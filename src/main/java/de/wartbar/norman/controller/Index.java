package de.wartbar.norman.controller;

import de.wartbar.norman.data.DataBundle;
import de.wartbar.norman.data.DataPreparator;
import de.wartbar.norman.data.WebDefaults;
import de.wartbar.norman.spring.data.persistence.DataBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
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

        ModelAndView modelAndView = WebDefaults.createModelAndView(db, "tableview");
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
        ModelAndView modelAndView = WebDefaults.createModelAndView(db, "tableviewlatestset");
        modelAndView.addObject("entities", bundle.list);
        modelAndView.addObject("columns", bundle.keyCounter);
        return modelAndView;
    }

    @RequestMapping(value="/latestsettoday", method = RequestMethod.GET)
    public ModelAndView indexLatestSetToday(@RequestParam Map<String,String> body) {
        if (!initialized) {
            initialize();
        }

        DataBundle bundle = DataPreparator.getLatestSetListToday(body, db);
        ModelAndView modelAndView = WebDefaults.createModelAndView(db, "tableviewlatestset");
        modelAndView.addObject("entities", bundle.list);
        modelAndView.addObject("columns", bundle.keyCounter);
        return modelAndView;
    }

    @RequestMapping(value="/deletetoday", method = RequestMethod.POST)
    public ModelAndView deleteToday() {
        db.deleteToday();
        return index(new HashMap<>());
    }

    @RequestMapping(value="/deleteall", method = RequestMethod.POST)
    public ModelAndView deleteAll() {
        db.deleteAll();
        return index(new HashMap<>());
    }

    @RequestMapping(value="/deleteselectedkeys", method = RequestMethod.POST)
    public ModelAndView deleteSelectedKeys(@RequestParam Map<String,String> body) {

        if (!initialized) {
            initialize();
        }

        DataBundle bundle = DataPreparator.getList(body, db);
        db.deleteList(bundle.list);

        return index(new HashMap<>());
    }

    @RequestMapping(value="/deletethis", method = RequestMethod.POST)
    public ModelAndView deleteThis(@RequestParam Map<String,String> body) {

        if (!initialized) {
            initialize();
        }

        db.deleteThis(body);

        return index(new HashMap<>());
    }

    @RequestMapping(value="/deletekeyset", method = RequestMethod.POST)
    public void deleteKeySet(@RequestParam Map<String,String> body) {

        if (!initialized) {
            initialize();
        }

        db.deleteKeySet(body);
    }

}
