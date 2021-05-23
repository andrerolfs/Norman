package de.wartbar.norman.controller;

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

        if (!body.isEmpty()) {
            String entityId = body.get("entityId");
            Long id = Long.parseLong(entityId);
            EntityModel rootEntity = db.get(id);

            log.debug("root id        : " + id);
            assert rootEntity != null;
            assert rootEntity.KEY01 != null;
            assert rootEntity.VALUE != null;
            assert rootEntity.keys != null;

            String entityKey = body.get("entityKey");
            int rootKeyIndex = Integer.parseInt(entityKey);
            log.debug("root key index : " + rootKeyIndex);
            log.debug("root key size  : " + rootEntity.keys.size());

            int keyCounter = 0;
            List<EntityModel> searchedEntities = new ArrayList<>();
            List<EntityModel> entities = db.getAll();
            for (EntityModel entity : entities) {
                if (rootKeyIndex >= entity.keys.size()) {
                    continue;
                }

                boolean add = true;
                for (int i = 0; i <=rootKeyIndex; i++) {

                    log.debug("access root key index : " + i);

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

            searchedEntities.sort(new EntityModelComparator());

            ModelAndView modelAndView = new ModelAndView("tableview");
            modelAndView.addObject("entities", searchedEntities);
            modelAndView.addObject("columns", keyCounter);
            return modelAndView;
        } else {
            List<EntityModel> list = new ArrayList<>(db.getAll());
            list.sort(new EntityModelComparator());

            ModelAndView modelAndView = new ModelAndView("tableview");
            modelAndView.addObject("entities", list);
            modelAndView.addObject("columns", db.getKeyCounterAll());
            return modelAndView;
        }
    }
}
