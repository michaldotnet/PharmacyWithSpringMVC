package pl.michal.controller;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.michal.dao.UserDao;
import pl.michal.model.User;

import java.security.Principal;

@Controller
public class MenuController {

    UserDao userDao;

    public MenuController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String menu(Principal principal) {
        System.out.println(principal.getName());
       User loggedUser = userDao.findByUsername(principal.getName());
        return "menu";
    }








}

