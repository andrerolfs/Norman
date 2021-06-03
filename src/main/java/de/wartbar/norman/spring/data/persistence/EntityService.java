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
public class EntityService {

    @Autowired
    private EntityRepository entityRepository;

    public void save(EntityModel entityModel) {
        entityRepository.save(entityModel);
    }
    public void delete(EntityModel entityModel) {
        entityRepository.delete(entityModel);
    }

    public List<EntityModel> findAll() {
        return entityRepository.findAll();
    }
}
