package de.wartbar.norman.spring.data.persistence;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
