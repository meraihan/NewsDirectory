package com.agranibank.NewsDirectory.controller;

import com.agranibank.NewsDirectory.model.News;
import com.agranibank.NewsDirectory.service.AgencyService;
import com.agranibank.NewsDirectory.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Sayed Mahmud Raihan on 12/07/18.
 */

@Controller
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;
    @Autowired
    private AgencyService agencyService;

    @RequestMapping("/view")
    public String view(Model model) {
        List<News> newsList = newsService.findAllNews();
        model.addAttribute("newsList", newsList);
        return "news/view";
    }

    @RequestMapping(value = "/add")
    public String add(@ModelAttribute("add") News news, HttpServletRequest request, Model model) {
        if (request.getMethod().equals(RequestMethod.POST.toString())) {
            if (newsService.addAgency(news)) {
                model.addAttribute("success", "News Added Successfully");
                return "redirect:view";
            }  else {
                model.addAttribute("error", "News Add Failed");
            }
        }
        model.addAttribute("agencyList", agencyService.findAllAgency());
        return "news/add";
    }

    @RequestMapping("/edit")
    public String edit(@ModelAttribute("news") News news, HttpServletRequest request, Model model,
                       final RedirectAttributes redirectAttributes) {
        if(request.getMethod().equals((RequestMethod.POST).toString())) {
            if (newsService.update(news)) {
                redirectAttributes.addFlashAttribute("success", "Successfully Edited News..");
                return "redirect:view";
            } else {
                model.addAttribute("error", "Edit Failed !");
                return "redirect:edit?id=" + news.getId();
            }
        }
        model.addAttribute("agencyList", agencyService.findAllAgency());
        model.addAttribute("singleNews", newsService.findById(news.getId()));
        return "news/edit";
    }

    @RequestMapping(value = "/delete")
    public String delete( @ModelAttribute("news") News news, final RedirectAttributes redirectAttributes) {
        if (newsService.delete(news.getId())) {
            redirectAttributes.addFlashAttribute("success", "Successfully Deleted News..");
        } else {
            redirectAttributes.addFlashAttribute("error", "Delation Failed !");
        }
        return "redirect:view";
    }

}