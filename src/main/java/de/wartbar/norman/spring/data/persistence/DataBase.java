package de.wartbar.norman.spring.data.persistence;

import de.wartbar.norman.data.Entity;
import de.wartbar.norman.data.Keys;
import de.wartbar.norman.spring.data.persistence.EntityModel;
import de.wartbar.norman.spring.data.persistence.EntityRepository;
import de.wartbar.norman.spring.data.persistence.EntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;

import static de.wartbar.norman.data.Constants.*;

@Slf4j
@Component
public class DataBase {

    private static final Object syncObject = new Object();
    private static final Map<String, List<Entity>> mainKeyEntityMap = new HashMap<>();
    private static final Map<Long, Entity> uniqueIdEntityMap = new HashMap<>();
    private static Map<String, Integer> mainKeyKeyCounter = new HashMap<>();
    private static int keyCounterAll = 0;

    public static Map<String, List<Entity>> get() {
        return mainKeyEntityMap;
    }

    public static List<Entity> getAll() {
        List<Entity> entityList = new ArrayList<>();
        mainKeyEntityMap.values().forEach(entityList::addAll);
        return entityList;
    }
    public static Entity get(Long id) {
        return uniqueIdEntityMap.get(id);
    }

    public static int getKeyCounterAll() {
        return keyCounterAll;
    }

    public static void add(Map<String, String> params) {

        if (!params.containsKey(keyCount)) {
            log.error("these params contain no key count : " + params);
            return;
        }

        int keys = Integer.parseInt(params.get(keyCount));

        if (params.size() != keys + 2) {
            log.error("these params do not match to keys + key count : " + params);
            return;
        }

        Entity entity = new Entity();
        entity.value = params.get("VALUE");
        for (int i = 0; i < keys; i++) {
            entity.keys.add(params.get(keysArray[i].name()));
        }

        String mainKey = params.get(Keys.KEY1.name());

        synchronized (syncObject) {
            List<Entity> entityList = mainKeyEntityMap.get(mainKey);
            if (entityList == null) {
                entityList = new ArrayList<>();
            }
            entityList.add(entity);
            mainKeyEntityMap.put(mainKey, entityList);
            uniqueIdEntityMap.put(entity.id, entity);
            if (keyCounterAll < keys) {
                keyCounterAll = keys;
            }
            mainKeyKeyCounter.put(mainKey, keys);
        }

    }

    public static void remove(String mainKey) {
        synchronized (syncObject) {
            mainKeyEntityMap.remove(mainKey);
        }
    }
}
