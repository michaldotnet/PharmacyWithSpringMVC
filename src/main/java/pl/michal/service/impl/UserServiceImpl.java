package pl.michal.service.impl;



import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.michal.dao.Impl.UserDaoImpl;
import pl.michal.dao.UserDao;
import pl.michal.model.User;
import pl.michal.service.IUserService;


@Service
public class UserServiceImpl implements IUserService {

    private UserDao userDao;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDaoImpl userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    public void addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(user.getRole());
        userDao.addUserToDataBase(user);
    }
}
