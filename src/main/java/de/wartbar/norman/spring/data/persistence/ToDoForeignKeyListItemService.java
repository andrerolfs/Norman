package de.wartbar.norman.spring.data.persistence;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Service
public class ToDoForeignKeyListItemService {
    @Autowired
    private ToDoForeignKeyListItemRepository todoforeignkeylistitemRepository;

    public void save(ToDoForeignKeyListItemModel todoforeignkeylistitemModel) {
        todoforeignkeylistitemRepository.save(todoforeignkeylistitemModel);
    }

    public List<ToDoForeignKeyListItemModel> findAll() {
        return todoforeignkeylistitemRepository.findAll();
    }

    public List<ToDoForeignKeyListItemModel> findByListId(Long listId) {
        return todoforeignkeylistitemRepository.findByListId(listId);
    }

    public List<ToDoForeignKeyListItemModel> findByListIdAndItemId(Long listId, Long itemId) {
        return todoforeignkeylistitemRepository.findByListIdAndItemId(listId, itemId);
    }

    public void deleteById(Long id) {
        todoforeignkeylistitemRepository.deleteById(id);
    }



}
