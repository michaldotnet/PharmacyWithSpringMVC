package pl.michal.controller.userController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.michal.model.User;
import pl.michal.service.IUserService;

@Controller
@RequestMapping("/admin")
public class AddUserController {

    IUserService iUserService;

    @Autowired
    public AddUserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public String addUserPage(Model model){
        model.addAttribute("errorMessage", "");
        model.addAttribute("userKey", new User());
        return ("addUser");
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String sendUserInfoToServer(@ModelAttribute("userKey") User user, Model model){
        if (user.getUsername().equals(" ") || user.getPassword().equals(" ")) {
            model.addAttribute("errorMessage", "Musisz wypełnić wszystkie pola.");
            //model.addAttribute("medicineKey", new Medicine());
            return ("addUser");
        }else{
            iUserService.addUser(user);
        }
            return "redirect:/";
    }
}
