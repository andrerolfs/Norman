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
public class ToDoForeignKeyUserListService {
    @Autowired
    private ToDoForeignKeyUserListRepository todoforeignkeyuserlistRepository;

    public void save(ToDoForeignKeyUserListModel todoforeignkeyuserlistModel) {
        todoforeignkeyuserlistRepository.save(todoforeignkeyuserlistModel);
    }

    public List<ToDoForeignKeyUserListModel> findByUserId(Long userId) {
        return todoforeignkeyuserlistRepository.findByUserId(userId);
    }

    public List<ToDoForeignKeyUserListModel> findAll() {
        return todoforeignkeyuserlistRepository.findAll();
    }

    public void deleteById(Long id) { todoforeignkeyuserlistRepository.deleteById(id);}
}
