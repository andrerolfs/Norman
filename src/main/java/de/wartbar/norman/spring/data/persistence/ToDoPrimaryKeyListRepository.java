package de.wartbar.norman.spring.data.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ToDoPrimaryKeyListRepository extends JpaRepository<ToDoPrimaryKeyListModel, Long> {

    public List<ToDoPrimaryKeyListModel> findByIdIn(List<Long> userIdList);

}
