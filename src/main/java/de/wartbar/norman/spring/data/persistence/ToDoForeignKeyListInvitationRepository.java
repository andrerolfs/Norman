package de.wartbar.norman.spring.data.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ToDoForeignKeyListInvitationRepository  extends JpaRepository<ToDoForeignKeyListInvitationModel, Long> {

    List<ToDoForeignKeyListInvitationModel> findByOfferingUserId(Long offeringUserId);

}
