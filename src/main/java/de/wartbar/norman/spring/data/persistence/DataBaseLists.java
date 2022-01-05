package de.wartbar.norman.spring.data.persistence;

import de.wartbar.norman.data.Constants;
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

    @Autowired
    ToDoForeignKeyListInvitationService listInvitationService;

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
            ToDoPrimaryKeyItemModel itemModel = itemService.findById(listItemModel.getItemId()).get();
            itemModel.setListItemId(listItemModel.getId());
            items.add(itemModel);
        }
        return items;
    }

    public ToDoPrimaryKeyItemModel saveItem(Map<String,String> body) {
        String itemName = body.get(Constants.itemName);
        String itemNameUp = itemName.toUpperCase();

        Long listId = Long.parseLong(body.get(Constants.listId));

        ToDoPrimaryKeyItemModel itemModel = null;

        for (ToDoPrimaryKeyItemModel current : itemService.findAll()) {
            if (current.getName().toUpperCase().equals(itemNameUp)) {
                itemModel = current;
                break;
            }
        }

        if (itemModel == null) {
            itemModel = new ToDoPrimaryKeyItemModel();
            itemModel.setName(itemName);
            itemModel.setUserId(dataBaseUser.findByUserName().getId());
            itemService.save(itemModel);
        }

        ToDoForeignKeyListItemModel listItemModel = new ToDoForeignKeyListItemModel();
        listItemModel.setItemId(itemModel.getId());
        listItemModel.setListId(listId);
        listItemService.save(listItemModel);
        return itemModel;
    }

    public ToDoPrimaryKeyItemModel editItem(Map<String,String> body) {
        Long itemId = Long.parseLong(body.get(Constants.itemId));
        ToDoPrimaryKeyItemModel itemModel = itemService.findById(itemId).get();
        String itemName = body.get(Constants.itemName);
        itemModel.setName(itemName);
        itemService.save(itemModel);
        return itemModel;
    }

    public void deleteListItem(Map<String,String> body) {
        Long listItemId = Long.parseLong(body.get(Constants.listItemId));
        listItemService.deleteById(listItemId);
    }

    public List<ToDoPrimaryKeyItemModel> getItems() {
        Long userId = dataBaseUser.findByUserName().getId();
        return itemService.findByUserId(userId);
    }

    public ToDoForeignKeyListItemModel addItem(Map<String,String> body) {
        Long listId = Long.parseLong(body.get(Constants.listId));
        Long itemId = Long.parseLong(body.get(Constants.itemId));

        ToDoForeignKeyListItemModel listItemModel = new ToDoForeignKeyListItemModel();
        listItemModel.setListId(listId);
        listItemModel.setItemId(itemId);
        listItemService.save(listItemModel);
        return listItemModel;
    }

    public ToDoForeignKeyListInvitationModel invite(Map<String,String> body) {
        Long offeringUserId = dataBaseUser.findByUserName().getId();

        String invitedUserName = body.get(Constants.invitedUserName);
        UserModel invitedUserModel = dataBaseUser.findByUserName(invitedUserName);

        Long listId = Long.parseLong(body.get(Constants.listId));

        ToDoForeignKeyListInvitationModel listInvitationModel = null;

        for (ToDoForeignKeyListInvitationModel current : listInvitationService.findAll()) {
            if (!current.getOfferingUserId().equals(offeringUserId)) {
                continue;
            }

            if (!current.getListId().equals(listId)) {
                continue;
            }

            if (!current.getInvitedUserId().equals(invitedUserModel.getId())) {
                continue;
            }

            return current;
        }

        if (listInvitationModel == null) {
            listInvitationModel = new ToDoForeignKeyListInvitationModel();
            listInvitationModel.setListId(listId);
            listInvitationModel.setInvitedUserId(invitedUserModel.getId());
            listInvitationModel.setOfferingUserId(offeringUserId);
            listInvitationService.save(listInvitationModel);
        }

        return listInvitationModel;
    }

    public List<ToDoForeignKeyListInvitationModel> getInvitations() {
        Long userId = dataBaseUser.findByUserName().getId();
        List<ToDoForeignKeyListInvitationModel> invitationModels = listInvitationService.findAll();
        for (ToDoForeignKeyListInvitationModel invitationModel : invitationModels) {
            if (invitationModel.getInvitedUserId().equals(userId) || invitationModel.getOfferingUserId().equals(userId)) {
                invitationModel.setInvitedUserName(dataBaseUser.findByUserId(invitationModel.getInvitedUserId()).getUsername());
                invitationModel.setOfferingUserName(dataBaseUser.findByUserId(invitationModel.getOfferingUserId()).getUsername());
                invitationModel.setListName(listService.findById(invitationModel.getListId()).get().getName());
            }
        }
        return invitationModels;
    }

    public void deleteInvitation(Map<String,String> body) {
        listInvitationService.deleteById(Long.parseLong(body.get(Constants.invitationId)));
    }

    public void acceptInvitation(Map<String,String> body) {
        ToDoForeignKeyListInvitationModel invitationModel = listInvitationService.findById(Long.parseLong(body.get(Constants.invitationId))).get();

        ToDoForeignKeyUserListModel userListModel = new ToDoForeignKeyUserListModel();
        userListModel.setListId(invitationModel.getListId());
        userListModel.setUserId(invitationModel.getInvitedUserId());
        userListService.save(userListModel);

        listInvitationService.deleteById(Long.parseLong(body.get(Constants.invitationId)));
    }

}
