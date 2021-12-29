package de.wartbar.norman.spring.data.persistence;

import java.util.Comparator;

public class ToDoForeignKeyListItemModelComparator implements Comparator<ToDoForeignKeyListItemModel> {
    @Override
    public int compare(ToDoForeignKeyListItemModel o1, ToDoForeignKeyListItemModel o2) {
        return o1.getId().compareTo(o2.getId());
    }
}
