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
public class ToDoPrimaryKeyListService {

    @Autowired
    private ToDoPrimaryKeyListRepository todoprimarykeylistRepository;

    public void save(ToDoPrimaryKeyListModel todoprimarykeylistModel) {
        todoprimarykeylistRepository.save(todoprimarykeylistModel);
    }

    public List<ToDoPrimaryKeyListModel> findByIdIn(List<Long> userIdList) {
        return todoprimarykeylistRepository.findByIdIn(userIdList);
    }

    public List<ToDoPrimaryKeyListModel> findAll() {
        return todoprimarykeylistRepository.findAll();
    }

    public Optional<ToDoPrimaryKeyListModel> findById(Long id) {
        return todoprimarykeylistRepository.findById(id);
    }

}
