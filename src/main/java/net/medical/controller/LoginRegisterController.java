package net.medical.controller;

import net.medical.form.UserRequestForm;
import net.medical.model.User;
import net.medical.model.enums.UserRole;
import net.medical.page.Pages;
import net.medical.service.UserService;
import net.medical.util.ImageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LoginRegisterController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserService userService;

    @Autowired
    private ImageUtil imageUtil;

    @GetMapping("/login")
    public String login() {
        return LOGIN;
    }

    @GetMapping("/register")
    public String registerGet(Model model) {
        model.addAttribute("form", new UserRequestForm());
        return REGISTER;
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute("form") @Valid UserRequestForm form, BindingResult result) {
        LOGGER.debug("form : {}, errors count : {}", form, result.getFieldErrorCount());
        if (result.hasErrors()) {
            return REGISTER;
        } else if (!form.getRepeatPassword().equals(form.getPassword())) {
            result.addError(new FieldError("form","passwordsNotMatch",""));
            return REGISTER;
        }else if(userService.existsByUsername(form.getUsername())){
            result.addError(new FieldError("form","usernameExists",""));
            return REGISTER;
        }else if(form.getImage().isEmpty()){
            result.addError(new FieldError("form","imageError",""));
            return REGISTER;
        }else if(!imageUtil.isValidData(form.getImage().getContentType())){
            result.addError(new FieldError("form","imageFormatError",""));
            return REGISTER;
        }else {
            User user = User.builder()
                    .name(form.getName())
                    .surname(form.getSurname())
                    .username(form.getUsername())
                    .username(form.getUsername())
                    .password(form.getPassword())
                    .imgUrl("")
                    .role(UserRole.USER)
                    .build();
            userService.add(user,form.getImage());
            return "redirect:/login";
        }
    }
}
