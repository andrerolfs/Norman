package de.wartbar.norman.spring.data.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ToDoForeignKeyUserListRepository extends JpaRepository<ToDoForeignKeyUserListModel, Long> {

    List<ToDoForeignKeyUserListModel> findByUserId(Long userId);

    List<ToDoForeignKeyUserListModel> findByListId(Long listId);
}
