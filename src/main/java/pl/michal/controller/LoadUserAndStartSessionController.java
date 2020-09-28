package pl.michal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.michal.dao.UserDao;
import pl.michal.model.User;

import java.security.Principal;

@Controller
@SessionAttributes("loggedUser")
public class LoadUserAndStartSessionController {

    UserDao userDao;

    public LoadUserAndStartSessionController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loadUserAndRedirect(Principal principal, Model model) {


        User loggedUser = userDao.findByUsername(principal.getName());
        model.addAttribute("loggedUser", loggedUser);
        System.out.println(loggedUser.getUsername());
        return "redirect:/menu";

    }
}
