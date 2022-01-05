package de.wartbar.norman.spring.data.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ToDoForeignKeyListItemRepository extends JpaRepository<ToDoForeignKeyListItemModel, Long> {

    List<ToDoForeignKeyListItemModel> findByListId(Long listId);

    List<ToDoForeignKeyListItemModel> findByListIdAndItemId(Long listId, Long itemId);
}
