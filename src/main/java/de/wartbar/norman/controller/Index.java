package de.wartbar.norman.controller;

import de.wartbar.norman.data.DataBase;
import de.wartbar.norman.data.Entity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Index {

    static boolean initialized = false;

    void initialize() {
        Map<String, String> params = new HashMap<>();

        params.put("KEYCOUNT", "3");
        params.put("KEY1", "KEY Name 1");
        params.put("KEY2", "KEY Name 2");
        params.put("KEY3", "KEY Name 3");
        params.put("VALUE", "value 3");
        DataBase.add(params);
        DataBase.add(params);
        DataBase.add(params);


        params.put("KEYCOUNT", "5");
        params.put("KEY1", "KEY Name 1");
        params.put("KEY2", "KEY Name 2");
        params.put("KEY3", "KEY Name 3");
        params.put("KEY4", "KEY Name 4");
        params.put("KEY5", "KEY Name 5");
        params.put("VALUE", "value 5");
        DataBase.add(params);
        DataBase.add(params);

        params.put("KEYCOUNT", "7");
        params.put("KEY1", "KEY Name 1");
        params.put("KEY2", "KEY Name 2");
        params.put("KEY3", "KEY Name 3");
        params.put("KEY4", "KEY Name 4");
        params.put("KEY5", "KEY Name 5");
        params.put("KEY6", "KEY Name 6");
        params.put("KEY7", "KEY Name 7");
        params.put("VALUE", "value 7");
        DataBase.add(params);
        DataBase.add(params);

        initialized = true;
    }

    @RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView index() {
        if (!initialized) {
            initialize();
        }

        ModelAndView modelAndView = new ModelAndView("tableview");
        modelAndView.addObject("entities", DataBase.getAll());
        modelAndView.addObject("columns", DataBase.getKeyCounterAll());
        return modelAndView;
    }
}
