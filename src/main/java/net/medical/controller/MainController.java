package net.medical.controller;

import net.medical.model.User;
import net.medical.model.enums.UserRole;
import net.medical.page.Pages;
import net.medical.service.PageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

@Controller
public class MainController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private PageService pageService;

    @GetMapping("")
    public String main(@RequestAttribute("user")User user, Model model){
        if(user.getRole().equals(UserRole.ADMIN)){
            LOGGER.debug("role 'ADMIN' redirect to '/admin' url");
            return "redirect:/admin";
        }else {
            model.addAttribute("main",pageService.getMain());
            return INDEX;
        }
    }
}
