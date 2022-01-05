package de.wartbar.norman.spring.data.persistence;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Service
public class ToDoForeignKeyListInvitationService {

    @Autowired
    private ToDoForeignKeyListInvitationRepository toDoForeignKeyListInvitationRepository;

    public void save(ToDoForeignKeyListInvitationModel toDoForeignKeyListInvitationModel) {
        toDoForeignKeyListInvitationRepository.save(toDoForeignKeyListInvitationModel);
    }

    public List<ToDoForeignKeyListInvitationModel> findAll() {
        return toDoForeignKeyListInvitationRepository.findAll();
    }

    public Optional<ToDoForeignKeyListInvitationModel> findById(Long id) {
        return toDoForeignKeyListInvitationRepository.findById(id);
    }

    public void deleteById(Long id) {
        toDoForeignKeyListInvitationRepository.deleteById(id);
    }

    public List<ToDoForeignKeyListInvitationModel> findByOfferingUserId(Long id) {
        return toDoForeignKeyListInvitationRepository.findByOfferingUserId(id);
    }
}
