package pl.michal.controller.errorController;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorPageController implements ErrorController {

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String showErrorPage(){
        return ("errorPage");
    }

    @RequestMapping(value = "/error", method = RequestMethod.POST)
    public String backToMenu(){
        return ("redirect:/menu");
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
