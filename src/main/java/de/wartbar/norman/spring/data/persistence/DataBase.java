package de.wartbar.norman.spring.data.persistence;

import de.wartbar.norman.data.Constants;
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

    @Autowired
    private JenkinsConfigService jenkinsConfigService;

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

    public void deleteList(List<EntityModel> list) {
        for (EntityModel entity : list) {
            entityService.delete(entity);
        }

        mainKeyEntityMap.clear();
        uniqueIdEntityMap.clear();
        keyCounterAll = 0;
        initialize();
    }

    public void deleteThis(Map<String,String> body) {
        String entityId = body.get("entityId");
        Long id = Long.parseLong(entityId);
        EntityModel entity = get(id);

        entityService.delete(entity);

        mainKeyEntityMap.clear();
        uniqueIdEntityMap.clear();
        keyCounterAll = 0;
        initialize();
    }

    public void deleteKeySet(Map<String,String> body) {
        log.debug("deleteKeySet");
        List<String> keySet = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            String key = String.format("KEY%02d", i);
            log.debug("Check " + key);
            String value = body.get(key);
            if (value != null) {
                keySet.add(value);
                log.debug("Key : " + value);
            } else {
                break;
            }
        }

        List<EntityModel> toBeDeleted = new ArrayList<>();

        for (EntityModel entity : getAll()) {
            if(entity.getKeys().size() < keySet.size()) {
                log.debug("entity too small : " + entity.getKeys().toString());
                continue;
            }
            boolean delete = true;
            for (int i = 0; i < keySet.size(); i++) {
                if (!keySet.get(i).equals(entity.getKeys().get(i))) {
                    log.debug("entity not equal : " + entity.getKeys().toString());
                    delete = false;
                    break;
                }
            }
            if (delete) {
                toBeDeleted.add(entity);
                log.debug("toBeDeleted : " + entity.getKeys().toString());
            }
        }

        for (EntityModel entity : toBeDeleted) {
            entityService.delete(entity);
        }

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

    public JenkinsConfigModel getJenkinsConfigModel() {
        List<JenkinsConfigModel> list = jenkinsConfigService.findAll();
        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    public void saveJenkinsConfigModel(Map<String,String> body) {
        JenkinsConfigModel model = getJenkinsConfigModel();
        if (model == null) {
            model = new JenkinsConfigModel();
        }

        model.name = body.get(name);
        model.ipAddress = body.get(ipaddress);
        model.port = body.get(port);
        model.username = body.get(username);
        model.password = body.get(password);
        jenkinsConfigService.save(model);
    }
}
