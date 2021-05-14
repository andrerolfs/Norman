package de.wartbar.norman.spring.data.persistence;

import de.wartbar.norman.data.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

import static de.wartbar.norman.data.Constants.*;

@Slf4j
@Component
public class DataBase {

    private static final Object syncObject = new Object();
    private static final Map<String, List<EntityModel>> mainKeyEntityMap = new HashMap<>();
    private static final Map<Long, EntityModel> uniqueIdEntityMap = new HashMap<>();
    private static int keyCounterAll = 0;

    @Autowired
    private EntityService entityService;

    public Map<String, List<EntityModel>> get() {
        return mainKeyEntityMap;
    }

    public List<EntityModel> getAll() {
        List<EntityModel> entityList = new ArrayList<>();
        mainKeyEntityMap.values().forEach(entityList::addAll);
        return entityList;
    }

    public EntityModel get(Long id) {
        return uniqueIdEntityMap.get(id);
    }

    public int getKeyCounterAll() {
        return keyCounterAll;
    }

    public void add(Map<String, String> params) {

        if (!params.containsKey(keyCount)) {
            log.error("these params contain no key count : " + params);
            return;
        }

        int keys = Integer.parseInt(params.get(keyCount));

        if (params.size() != keys + 2) {
            log.error("these params do not match to keys + key count : " + params);
            return;
        }

        EntityModel entity = new EntityModel();
        entity.VALUE = params.get(value);
        entity.DATE = new Date();
        entity.keys = new ArrayList<>();

        for (int i = 0; i < keys; i++) {
            String key = params.get(keysArray[i].name());
            entity.keys.add(key);
            try {
                // use reflection so we don´t need to hard code this for 20 cases
                Field keyField = entity.getClass().getDeclaredField(Keys.values()[i].name());
                keyField.setAccessible(true);
                keyField.set(entity, key);
            } catch(NoSuchFieldException e) {
                log.error("NoSuchFieldException", e);
                return;
            } catch (IllegalAccessException e)  {
                log.error("IllegalAccessException", e);
                return;
            }
        }

        entityService.save(entity);

        String mainKey = params.get(Keys.KEY01.name());

        synchronized (syncObject) {
            List<EntityModel> entityList = mainKeyEntityMap.get(mainKey);
            if (entityList == null) {
                entityList = new ArrayList<>();
            }
            entityList.add(entity);
            mainKeyEntityMap.put(mainKey, entityList);
            uniqueIdEntityMap.put(entity.getId(), entity);
            if (keyCounterAll < keys) {
                keyCounterAll = keys;
            }
        }
    }

    private void initializeEntityKeysList(EntityModel entity) {
        entity.keys = new ArrayList<>();
        for (int i = 0; i < Keys.values().length; i++) {
            try {
                // use reflection so we don´t need to hard code this for 20 cases
                Field keyField = entity.getClass().getDeclaredField(Keys.values()[i].name());
                keyField.setAccessible(true);
                String keyValue = (String)keyField.get(entity);
                if (keyValue != null)
                {
                    entity.keys.add(keyValue);
                } else {
                    break;
                }
            } catch(NoSuchFieldException e) {
                log.error("NoSuchFieldException", e);
                return;
            } catch (IllegalAccessException e)  {
                log.error("IllegalAccessException", e);
                return;
            }
        }
    }

    public void initialize() {
        List<EntityModel> allEntities = entityService.findAll();
        for (EntityModel entity : allEntities) {
            initializeEntityKeysList(entity);
            List<EntityModel> listForKey = mainKeyEntityMap.get(entity.KEY01);
            if(listForKey == null) {
                listForKey = new ArrayList<>();
            }
            listForKey.add(entity);
            mainKeyEntityMap.put(entity.KEY01, listForKey);
            uniqueIdEntityMap.put(entity.getId(), entity);
        }

        Map<String, String> params = new HashMap<>();

        params.put("KEYCOUNT", "3");
        params.put("KEY01", "KEY 1 1");
        params.put("KEY02", "KEY 1 2");
        params.put("KEY03", "KEY 1 3");
        params.put("VALUE", "value 3");
        add(params);
        add(params);
        add(params);

        params.put("KEYCOUNT", "5");
        params.put("KEY01", "KEY 2 1");
        params.put("KEY02", "KEY 2 2");
        params.put("KEY03", "KEY 2 3");
        params.put("KEY04", "KEY 2 4");
        params.put("KEY05", "KEY 2 5");
        params.put("VALUE", "value 5");
        add(params);
        add(params);

        params.put("KEYCOUNT", "5");
        params.put("KEY01", "KEY 1 1");
        params.put("KEY02", "KEY 1 2");
        params.put("KEY03", "KEY 2 3");
        params.put("KEY04", "KEY 2 4");
        params.put("KEY05", "KEY 2 5");
        params.put("VALUE", "value 5.1");
        add(params);

        params.put("KEYCOUNT", "7");
        params.put("KEY01", "KEY 3 1");
        params.put("KEY02", "KEY 3 2");
        params.put("KEY03", "KEY 3 3");
        params.put("KEY04", "KEY 3 4");
        params.put("KEY05", "KEY 3 5");
        params.put("KEY06", "KEY 3 6");
        params.put("KEY07", "KEY 3 7");
        params.put("VALUE", "value 7");
        add(params);
        add(params);

        params.put("KEYCOUNT", "7");
        params.put("KEY01", "KEY 1 1");
        params.put("KEY02", "KEY 1 2");
        params.put("KEY03", "KEY 3 3");
        params.put("KEY04", "KEY 3 4");
        params.put("KEY05", "KEY 3 5");
        params.put("KEY06", "KEY 3 6");
        params.put("KEY07", "KEY 3 7");
        params.put("VALUE", "value 7.2");
        add(params);
    }
}
