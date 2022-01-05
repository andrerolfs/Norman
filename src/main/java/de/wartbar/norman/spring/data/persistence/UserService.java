package de.wartbar.norman.spring.data.persistence;

import java.util.List;

public interface UserService {
    void save(UserModel userModel);

    UserModel findByUsername(String username);

    UserModel findById(Long userId);

    List<UserModel> findAll();
}
