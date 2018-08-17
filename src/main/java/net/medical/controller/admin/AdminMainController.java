package net.medical.controller.admin;

import net.medical.model.User;
import net.medical.model.enums.UserRole;
import net.medical.page.Pages;
import net.medical.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminMainController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserService userService;

    @GetMapping
    public String main(Model model){
        model.addAttribute("users",userService.findAllByRole(UserRole.USER));
        return ADMIN;
    }

    @PostMapping("/user/delete")
    public String deleteUser(@RequestParam("userId") int id){
        LOGGER.debug("userId : {}",id);
        User user = userService.deleteById(id);
        if(user == null){
            return "redirect:/admin";
        }else if(user.getRole().equals(UserRole.USER)){
            return "redirect:/admin";
        }else {
            return "redirect:/admin/doctors";
        }
    }
}
