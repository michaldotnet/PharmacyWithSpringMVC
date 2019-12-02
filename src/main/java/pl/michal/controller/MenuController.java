package pl.michal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MenuController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String menu() {
        return "menu";
    }








}

