package de.wartbar.norman.spring.data.persistence;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
