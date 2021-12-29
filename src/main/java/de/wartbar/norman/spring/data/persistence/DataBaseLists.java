package de.wartbar.norman.spring.data.persistence;

import de.wartbar.norman.data.Constants;
import de.wartbar.norman.data.WebDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class DataBaseLists {

    @Autowired
    DataBaseUser dataBaseUser;

    @Autowired
    ToDoPrimaryKeyListService listService;

    @Autowired
    ToDoPrimaryKeyItemService itemService;

    @Autowired
    ToDoForeignKeyUserListService userListService;

    @Autowired
    ToDoForeignKeyListItemService listItemService;

    public Long getUserId() {
        return dataBaseUser.findByUserName().getId();
    }

    public List<ToDoPrimaryKeyListModel> getLists() {
        List<ToDoForeignKeyUserListModel> filteredTodoLists = userListService.findByUserId(getUserId());

        List<Long> listsOfThisUser = new ArrayList<>();

        for (ToDoForeignKeyUserListModel userList : filteredTodoLists) {
            listsOfThisUser.add(userList.getListId());
        }

        return listService.findByIdIn(listsOfThisUser);
    }

    public Optional<ToDoPrimaryKeyListModel> getList(Long listId) {
        return listService.findById(listId);
    }

    public void saveList(Map<String,String> body) {
        ToDoPrimaryKeyListModel listModel = new ToDoPrimaryKeyListModel();
        listModel.setName(body.get(Constants.listName));
        listService.save(listModel);

        ToDoForeignKeyUserListModel userListModel = new ToDoForeignKeyUserListModel();
        userListModel.setUserId(getUserId());
        userListModel.setListId(listModel.getId());
        userListService.save(userListModel);
    }

    public List<ToDoPrimaryKeyItemModel> getItems(Long listId) {
        Optional<ToDoPrimaryKeyListModel> listModel = getList(listId);
        List<ToDoForeignKeyListItemModel> listItems = listItemService.findByListId(listModel.get().getId());
        List<ToDoPrimaryKeyItemModel> items = new ArrayList<>();
        for (ToDoForeignKeyListItemModel listItemModel : listItems) {
            items.add(itemService.findById(listItemModel.getItemId()).get());
        }
        return items;
    }

    public ToDoPrimaryKeyItemModel saveItem(Map<String,String> body) {
        String itemName = body.get(Constants.itemName);
        String itemNameUp = itemName.toUpperCase();

        Long listId = Long.parseLong(body.get(Constants.listId));

        for (ToDoPrimaryKeyItemModel current : itemService.findAll()) {
            if (current.getName().toUpperCase().equals(itemNameUp)) {
                return current;
            }
        }

        ToDoPrimaryKeyItemModel itemModel = new ToDoPrimaryKeyItemModel();
        itemModel.setName(itemName);
        itemModel.setUserId(dataBaseUser.findByUserName().getId());
        itemService.save(itemModel);

        ToDoForeignKeyListItemModel listItemModel = new ToDoForeignKeyListItemModel();
        listItemModel.setItemId(itemModel.getId());
        listItemModel.setListId(listId);
        listItemService.save(listItemModel);
        return itemModel;
    }

}
