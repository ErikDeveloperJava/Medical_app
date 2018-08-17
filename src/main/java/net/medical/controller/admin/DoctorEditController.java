package net.medical.controller.admin;

import net.medical.form.DoctorRequestForm;
import net.medical.form.UserRequestForm;
import net.medical.model.*;
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
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class DoctorEditController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserService userService;

    @Autowired
    private ImageUtil imageUtil;

    @GetMapping("/doctor/add")
    public String addDoctorGet(Model model){
        model.addAttribute("form",new DoctorRequestForm());
        return ADD_DOCTOR;
    }


    @PostMapping("/doctor/add")
    public String addDoctorPost(@ModelAttribute("form") @Valid DoctorRequestForm form, BindingResult result) {
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
            Doctor doctor = getDoctor(form);
            List<WorkingDays> workingDays = getWorkingDays(form.getDays());
            User user = User.builder()
                    .name(form.getName())
                    .surname(form.getSurname())
                    .username(form.getUsername())
                    .username(form.getUsername())
                    .password(form.getPassword())
                    .imgUrl("")
                    .role(UserRole.DOCTOR)
                    .build();
            userService.addDoctor(user,doctor,workingDays,form.getImage());
            return "redirect:/admin";
        }
    }

    private Doctor getDoctor(DoctorRequestForm form){
        return Doctor.builder()
                .information(form.getInformation())
                .department(Department.builder().id(form.getDepartment()).build())
                .build();
    }

    private List<WorkingDays> getWorkingDays(List<String> values){
        List<WorkingDays> workingDays = new ArrayList<>();
        int id = 1;
        for (String value : values) {
            WorkingDays days = WorkingDays.builder()
                    .days(Days.builder().id(id++).build())
                    .doctor(Doctor.builder().build())
                    .workingHours(value)
                    .build();
            workingDays.add(days);
        }
        return workingDays;
    }
}
