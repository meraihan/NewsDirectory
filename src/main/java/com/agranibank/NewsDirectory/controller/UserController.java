package com.agranibank.NewsDirectory.controller;

import com.agranibank.NewsDirectory.model.User;
import com.agranibank.NewsDirectory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/view")
    public String view(Model model) {
        List<User> userList = userService.findAllUser();
        model.addAttribute("userList", userList);
        return "user/view";
    }

    @RequestMapping(value = "/add")
    public String add(@ModelAttribute("add") User user, HttpServletRequest request, Model model) {
        if (request.getMethod().equals(RequestMethod.POST.toString())) {
            if (userService.addUser(user)) {
                model.addAttribute("success", "User Added Successfully");
                return "redirect:view";
            }  else {
                model.addAttribute("error", "User Add Failed");
            }
        }
        return "user/add";
    }

    @RequestMapping("/edit")
    public String edit(@ModelAttribute("edit") User user, HttpServletRequest request, Model model,
                       final RedirectAttributes redirectAttributes) {
        if(request.getMethod().equals((RequestMethod.POST).toString())) {
            if (userService.update(user)) {
                redirectAttributes.addFlashAttribute("success", "Successfully Edited User..");
                return "redirect:view";
            } else {
                model.addAttribute("error", "Edit Failed !");
                return "redirect:edit?id=" + user.getId();
            }
        }
        model.addAttribute("singleUser", userService.findById(user.getId()));
        return "user/edit";
    }

    @RequestMapping(value = "/delete")
    public String delete( @ModelAttribute("user") User user, final RedirectAttributes redirectAttributes) {
        if (userService.delete(user.getId())) {
            redirectAttributes.addFlashAttribute("success", "Successfully Deleted User..");
        } else {
            redirectAttributes.addFlashAttribute("error", "Delation Failed !");
        }
        return "redirect:view";
    }

}
