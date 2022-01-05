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
public class ToDoPrimaryKeyItemService {
    @Autowired
    private ToDoPrimaryKeyItemRepository todoprimarykeyitemRepository;

    public void save(ToDoPrimaryKeyItemModel todoprimarykeyitemModel) {
        todoprimarykeyitemRepository.save(todoprimarykeyitemModel);
    }

    public List<ToDoPrimaryKeyItemModel> findAll() {
        return todoprimarykeyitemRepository.findAll();
    }

    public Optional<ToDoPrimaryKeyItemModel> findById(Long itemId) {
        return todoprimarykeyitemRepository.findById(itemId);
    }

    public List<ToDoPrimaryKeyItemModel> findByUserId(Long userId) {
        return todoprimarykeyitemRepository.findByUserId(userId);
    }

    public List<ToDoPrimaryKeyItemModel> findByUserIdAndName(Long userId, String name) {
        return todoprimarykeyitemRepository.findByUserIdAndName(userId, name);
    }



}
