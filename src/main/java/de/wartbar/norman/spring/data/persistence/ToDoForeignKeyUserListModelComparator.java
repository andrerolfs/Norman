package de.wartbar.norman.spring.data.persistence;

import java.util.Comparator;

public class ToDoForeignKeyUserListModelComparator implements Comparator<ToDoForeignKeyUserListModel> {
    @Override
    public int compare(ToDoForeignKeyUserListModel o1, ToDoForeignKeyUserListModel o2) {
        return o1.getId().compareTo(o2.getId());
    }
}
