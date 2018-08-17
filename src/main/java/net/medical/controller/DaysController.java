package net.medical.controller;

import net.medical.page.Pages;
import net.medical.repository.DaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DaysController implements Pages {

    @Autowired
    private DaysRepository daysRepository;

    @PostMapping("/admin/days")
    public String days(Model model){
        model.addAttribute("days",daysRepository.findAll());
        return DAYS;
    }
}
