package de.wartbar.norman.spring.data.persistence;

import java.util.Comparator;

public class ToDoPrimaryKeyListModelComparator implements Comparator<ToDoPrimaryKeyListModel> {
    @Override
    public int compare(ToDoPrimaryKeyListModel o1, ToDoPrimaryKeyListModel o2) {
        return o1.getId().compareTo(o2.getId());
    }
}
