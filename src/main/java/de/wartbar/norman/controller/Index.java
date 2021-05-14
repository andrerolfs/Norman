package de.wartbar.norman.controller;

import de.wartbar.norman.spring.data.persistence.DataBase;
import de.wartbar.norman.data.Entity;
import de.wartbar.norman.spring.data.persistence.EntityModel;
import de.wartbar.norman.spring.data.persistence.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Index {

    static boolean initialized = false;

    @Autowired
    EntityService entityService;

    void initialize() {
        Map<String, String> params = new HashMap<>();

        params.put("KEYCOUNT", "3");
        params.put("KEY1", "KEY 1 1");
        params.put("KEY2", "KEY 1 2");
        params.put("KEY3", "KEY 1 3");
        params.put("VALUE", "value 3");
        DataBase.add(params);
        DataBase.add(params);
        DataBase.add(params);

        params.put("KEYCOUNT", "5");
        params.put("KEY1", "KEY 2 1");
        params.put("KEY2", "KEY 2 2");
        params.put("KEY3", "KEY 2 3");
        params.put("KEY4", "KEY 2 4");
        params.put("KEY5", "KEY 2 5");
        params.put("VALUE", "value 5");
        DataBase.add(params);
        DataBase.add(params);

        params.put("KEYCOUNT", "5");
        params.put("KEY1", "KEY 1 1");
        params.put("KEY2", "KEY 1 2");
        params.put("KEY3", "KEY 2 3");
        params.put("KEY4", "KEY 2 4");
        params.put("KEY5", "KEY 2 5");
        params.put("VALUE", "value 5.1");
        DataBase.add(params);

        params.put("KEYCOUNT", "7");
        params.put("KEY1", "KEY 3 1");
        params.put("KEY2", "KEY 3 2");
        params.put("KEY3", "KEY 3 3");
        params.put("KEY4", "KEY 3 4");
        params.put("KEY5", "KEY 3 5");
        params.put("KEY6", "KEY 3 6");
        params.put("KEY7", "KEY 3 7");
        params.put("VALUE", "value 7");
        DataBase.add(params);
        DataBase.add(params);

        params.put("KEYCOUNT", "7");
        params.put("KEY1", "KEY 1 1");
        params.put("KEY2", "KEY 1 2");
        params.put("KEY3", "KEY 3 3");
        params.put("KEY4", "KEY 3 4");
        params.put("KEY5", "KEY 3 5");
        params.put("KEY6", "KEY 3 6");
        params.put("KEY7", "KEY 3 7");
        params.put("VALUE", "value 7.2");
        DataBase.add(params);

        initialized = true;
    }

    @RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView index(@RequestParam Map<String,String> body) {
        if (!initialized) {
            initialize();
        }


        EntityModel entityModel = new EntityModel();
        entityModel.KEY1 = "key1";
        entityModel.KEY2 = "key2";
        entityModel.KEY3 = "key3";
        entityModel.VALUE = "VALUE";

        entityService.save(entityModel);

        if (!body.isEmpty()) {
            String entityId = body.get("entityId");
            Long id = Long.parseLong(entityId);
            Entity rootEntity = DataBase.get(id);

            String entityKey = body.get("entityKey");
            int rootKeyIndex = Integer.parseInt(entityKey);

            int keyCounter = 0;
            List<Entity> searchedEntities = new ArrayList<>();
            List<Entity> entities = DataBase.getAll();
            for (Entity entity : entities) {
                if (rootKeyIndex >= entity.keys.size()) {
                    continue;
                }

                boolean add = true;
                for (int i = 0; i <=rootKeyIndex; i++) {
                    if (entity.keys.get(i) != rootEntity.keys.get(i)) {
                        add = false;
                    }
                }
                if (add) {
                    if (keyCounter < entity.keys.size()) {
                        keyCounter = entity.keys.size();
                    }
                    searchedEntities.add(entity);
                }
            }

            ModelAndView modelAndView = new ModelAndView("tableview");
            modelAndView.addObject("entities", searchedEntities);
            modelAndView.addObject("columns", keyCounter);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("tableview");
            modelAndView.addObject("entities", DataBase.getAll());
            modelAndView.addObject("columns", DataBase.getKeyCounterAll());
            return modelAndView;
        }

    }
}
