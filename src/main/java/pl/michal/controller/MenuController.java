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
public class MenuController {

    public MenuController() {
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String menu(@SessionAttribute("loggedUser") User loggedUser, Model model) {

        model.addAttribute("loggedUser", loggedUser);


       if(loggedUser.getRole().equals("ROLE_ADMIN")){
           return "adminsMenuV2";
       }else if(loggedUser.getRole().equals("ROLE_USER")){
           return "userMenuV2";
       }

        return "menuV2";
    }








}

