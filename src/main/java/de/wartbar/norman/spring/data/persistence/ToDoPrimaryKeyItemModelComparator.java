package de.wartbar.norman.spring.data.persistence;

import java.util.Comparator;

public class ToDoPrimaryKeyItemModelComparator implements Comparator<ToDoPrimaryKeyItemModel> {
    @Override
    public int compare(ToDoPrimaryKeyItemModel o1, ToDoPrimaryKeyItemModel o2) {
        return o1.getId().compareTo(o2.getId());
    }
}
