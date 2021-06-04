package de.wartbar.norman.spring.data.persistence;

import de.wartbar.norman.data.Keys;
import de.wartbar.norman.util.DateTool;
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
        log.debug("add " + params.toString() );

        if (!params.containsKey(keyCount)) {
            log.error("these params contain no key count : " + params);
            return;
        }

        int keys = Integer.parseInt(params.get(keyCount));

        if (params.size() != keys + 4) {
            log.error("these params do not match to keys + key count + background_color + color : " + params);
            return;
        }

        EntityModel entity = new EntityModel();
        entity.VALUE = params.get(value);
        entity.DATE = new Date();
        entity.COLOR = params.get(color);
        entity.BACKGROUND_COLOR = params.get(backgroundColor);
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

    public void deleteToday() {
        synchronized (syncObject) {
            initialize();
            Date today = new Date();
            for (EntityModel entity : getAll()) {
                if (DateTool.equalsDate(today, entity.getDATE())) {
                    log.debug("Delete : " + entity.getId());
                    entityService.delete(entity);
                } else {
                    log.debug("Keep : " + entity.getId());
                }
            }
            mainKeyEntityMap.clear();
            uniqueIdEntityMap.clear();
            keyCounterAll = 0;
            initialize();
        }
    }

    public void deleteAll() {
        synchronized (syncObject) {
            initialize();
            for (EntityModel entity : getAll()) {
                entityService.delete(entity);
            }

            mainKeyEntityMap.clear();
            uniqueIdEntityMap.clear();
            keyCounterAll = 0;
            initialize();
        }
    }

    public void delete(List<EntityModel> list) {
        for (EntityModel entity : list) {
            entityService.delete(entity);
        }

        mainKeyEntityMap.clear();
        uniqueIdEntityMap.clear();
        keyCounterAll = 0;
        initialize();
    }

    public void delete(Map<String,String> body) {
        String entityId = body.get("entityId");
        Long id = Long.parseLong(entityId);
        EntityModel entity = get(id);

        entityService.delete(entity);

        mainKeyEntityMap.clear();
        uniqueIdEntityMap.clear();
        keyCounterAll = 0;
        initialize();
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

                    if (keyCounterAll < i+1) {
                        keyCounterAll = i+1;
                    }

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
    }
}
