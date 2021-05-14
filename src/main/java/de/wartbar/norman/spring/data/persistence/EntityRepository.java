package de.wartbar.norman.spring.data.persistence;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface EntityRepository extends JpaRepository<EntityModel, Long> {
}
