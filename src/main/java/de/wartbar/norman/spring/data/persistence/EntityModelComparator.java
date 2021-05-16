package de.wartbar.norman.spring.data.persistence;

import java.util.Comparator;

public class EntityModelComparator implements Comparator<EntityModel> {
    @Override
    public int compare(EntityModel o1, EntityModel o2) {
        return -o1.getId().compareTo(o2.getId());
    }
}

