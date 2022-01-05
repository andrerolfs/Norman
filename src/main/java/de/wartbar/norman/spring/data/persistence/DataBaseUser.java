package de.wartbar.norman.spring.data.persistence;

import de.wartbar.norman.data.WebDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class DataBaseUser {

    @Autowired
    UserService userService;

    public void save(UserModel user) {
        userService.save(user);
    }

    public UserModel findByUserName(String name) {
        return userService.findByUsername(name);
    }

    public UserModel findByUserId(Long id) {
        return userService.findById(id);
    }

    public UserModel findByUserName() {
        return userService.findByUsername(WebDefaults.getUsername());
    }

    public UserModel findByUserName(Map<String,String> body) {
        String userName = body.get("username");
        return findByUserName(userName);
    }

}
