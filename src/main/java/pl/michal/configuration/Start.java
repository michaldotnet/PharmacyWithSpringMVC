package pl.michal.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.michal.dao.Impl.UserDaoImpl;
import pl.michal.dao.UserDao;
import pl.michal.model.User;


@Configuration
public class Start {

    private UserDaoImpl userDao;

    public Start(UserDaoImpl userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;

        User user1 = new User();
        User user2 = new User();

        user1.setUsername("Jagoda");
        user1.setPassword(passwordEncoder.encode("tajne"));
        user1.setRole("ROLE_ADMIN");
        userDao.addUserToDataBase(user1);

        user2.setUsername("michal");
        user2.setPassword(passwordEncoder.encode("tajne"));
        user2.setRole("ROLE_USER");
        userDao.addUserToDataBase(user2);


    }


}
