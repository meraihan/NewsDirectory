package com.agranibank.NewsDirectory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Sayed Mahmud Raihan on 12/05/18.
 */

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcomePage(Model model) {
        model.addAttribute("title", "Welcome to News Directory");
        return "home/index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("title", "Welcome to News Directory");
        return "login/login";
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard(Model model) {
        model.addAttribute("title", "Welcome toNews Directory");
        return "home/home";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String error(Model model) {
        model.addAttribute("title", "ACCESS DENIED");
        return "error/403";
    }

}
