package de.wartbar.norman.spring.data.persistence;

import java.util.List;

public interface UserService {
    void save(UserModel userModel);

    UserModel findByUsername(String username);
    List<UserModel> findAll();
}
