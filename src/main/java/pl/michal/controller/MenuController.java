package pl.michal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.michal.dao.UserDao;
import pl.michal.model.User;

import java.security.Principal;

@Controller
@SessionAttributes("loggedUser")
public class MenuController {

    UserDao userDao;

    public MenuController(UserDao userDao)
    {
        this.userDao = userDao;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String menu(Principal principal, Model model) {


            User loggedUser = userDao.findByUsername(principal.getName());
        model.addAttribute("loggedUser", loggedUser);



           // System.out.println(principal.getName() + " has logged in.");
            //User loggedUser = userDao.findByUsername(principal.getName());

       if(loggedUser.getRole().equals("ROLE_ADMIN")){
           return "adminsMenu";
       }else if(loggedUser.getRole().equals("ROLE_USER")){
           return "userMenu";
       }

        return "menu";
    }








}

