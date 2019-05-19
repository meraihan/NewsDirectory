package com.agranibank.NewsDirectory.controller;

import com.agranibank.NewsDirectory.model.Agency;
import com.agranibank.NewsDirectory.service.AgencyService;
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
@RequestMapping("/agency")
public class AgencyController {

    @Autowired
    AgencyService agencyService;

    @RequestMapping(value = "/add")
    public String add(@ModelAttribute("add") Agency agency, HttpServletRequest request, Model model) {
        if (request.getMethod().equals(RequestMethod.POST.toString())) {
            if (agencyService.addAgency(agency)) {
                model.addAttribute("success", "Agency Added Successfully");
                return "redirect:view";
            }  else {
                model.addAttribute("error", "Agency Add Failed");
            }
        }
        return "agency/add";
    }

    @RequestMapping("/view")
    public String view(Model model) {
        List<Agency> agencyList = agencyService.findAllAgency();
        model.addAttribute("agencyList", agencyList);
        return "agency/view";
    }

    @RequestMapping("/edit")
    public String edit(@ModelAttribute("agency") Agency agency, HttpServletRequest request, Model model,
                       final RedirectAttributes redirectAttributes) {
        if(request.getMethod().equals((RequestMethod.POST).toString())) {
            if (agencyService.update(agency)) {
                redirectAttributes.addFlashAttribute("success", "Successfully Edited..");
                return "redirect:view";
            } else {
                model.addAttribute("error", "Edit Failed !");
                return "redirect:edit?id=" + agency.getId();
            }
        }
        model.addAttribute("singleAgency", agencyService.findById(agency.getId()));
        return "agency/edit";
    }

    @RequestMapping(value = "/delete")
    public String delete( @ModelAttribute("agency") Agency agency, final RedirectAttributes redirectAttributes) {
        if (agencyService.delete(agency.getId())) {
            redirectAttributes.addFlashAttribute("success", "Successfully Deleted..");
        } else {
            redirectAttributes.addFlashAttribute("error", "Delation Failed !");
        }
        return "redirect:view";
    }

}
