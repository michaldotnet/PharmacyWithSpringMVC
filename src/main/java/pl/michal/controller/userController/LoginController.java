package pl.michal.controller.userController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.michal.model.User;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String addUserPage(Model model){
        model.addAttribute("errorMessage", "");
        model.addAttribute("userKey", new User());
        return ("login");
    }


}
