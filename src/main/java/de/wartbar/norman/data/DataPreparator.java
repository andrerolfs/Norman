package de.wartbar.norman.data;

import de.wartbar.norman.spring.data.persistence.DataBase;
import de.wartbar.norman.spring.data.persistence.EntityModel;
import de.wartbar.norman.spring.data.persistence.EntityModelComparator;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class DataPreparator {

    public static DataBundle getList(Map<String,String> body, DataBase db) {

        if (body.isEmpty()) {
            DataBundle bundle = new DataBundle();
            bundle.list = db.getAll();
            bundle.keyCounter = db.getKeyCounterAll();
            bundle.list.sort(new EntityModelComparator());
            return bundle;
        } else {
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

                    if (!entity.keys.get(i).equals(rootEntity.keys.get(i))) {
                        log.debug("entity key : " + entity.keys.get(i) + " != rootEntity key : " + rootEntity.keys.get(i));
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

            DataBundle bundle = new DataBundle();
            bundle.list = searchedEntities;
            bundle.keyCounter = keyCounter;

            return bundle;
        }
    }

    private static String getFlatKey(EntityModel entity) {
        String flatKey = "";
        for (String key : entity.getKeys()) {
            flatKey += key;
        }
        return flatKey;
    }

    public static DataBundle getLatestSetList(Map<String,String> body, DataBase db) {
        Set<String> flatKeySet = new HashSet<>();
        List<EntityModel> outputList = new ArrayList<>();
        DataBundle inputBundle = getList(body, db);
        for (EntityModel entity : inputBundle.list) {
            String flatKey = getFlatKey(entity);
            if (flatKeySet.add(flatKey)) {
                outputList.add(entity);
            }
        }
        DataBundle outputBundle = new DataBundle();
        outputBundle.list = outputList;
        outputBundle.keyCounter = inputBundle.keyCounter;
        return outputBundle;
    }
}
