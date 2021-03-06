package de.wartbar.norman.spring.data.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    UserModel findByUsername(String username);

}

