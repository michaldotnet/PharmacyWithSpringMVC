package pl.michal.dao;

import org.springframework.stereotype.Repository;
import pl.michal.model.User;


import java.util.Optional;

public interface UserDao  {

    User findByUsername(String username);
    void addUserToDataBase(User user);


}
