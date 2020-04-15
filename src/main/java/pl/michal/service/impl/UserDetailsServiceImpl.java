package pl.michal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.michal.dao.Impl.UserDaoImpl;
import pl.michal.dao.UserDao;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {



    private UserDaoImpl userDao;

    @Autowired
    public UserDetailsServiceImpl(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userDao.findByUsername(s);
    }
}
