package net.medical.controller;

import net.medical.form.UserUpdateRequestForm;
import net.medical.model.User;
import net.medical.model.enums.UserRole;
import net.medical.page.Pages;
import net.medical.service.DepartmentService;
import net.medical.service.PageService;
import net.medical.service.UserService;
import net.medical.util.PageableUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class DoctorController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final int DEFAULT_SIZE = 4;

    @Autowired
    private PageService pageService;

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/doctor/{id}")
    public String doctor(@PathVariable("id")String doctorStrId,
                         @RequestAttribute("user")User user, Model model){
        LOGGER.debug("doctorId : {}",doctorStrId);
        int doctorId = getId(doctorStrId);
        if(doctorId == -1){
            return "redirect:/";
        }
        model.addAttribute("doctor",pageService.getDoctor(user,doctorId));
        if(user.getId() == doctorId) {
            UserUpdateRequestForm form = UserUpdateRequestForm.builder()
                    .name(user.getName())
                    .surname(user.getSurname())
                    .username(user.getUsername())
                    .build();
            model.addAttribute("form",form);
        }
        return DOCTOR_PROFILE;
    }

    private int getId(String doctorStrId) {
        int doctorId;
        try {
            doctorId = Integer.parseInt(doctorStrId);
            if(doctorId <= 0 || !userService.existsByIdAndRole(doctorId,UserRole.DOCTOR)){
                doctorId = -1;
            }
        }catch (NumberFormatException e){
            doctorId = -1;
        }
        return doctorId;
    }

    @PostMapping("/doctor/working-days/update")
    public String updateWorkingDays(@RequestParam("workingDays")List<String> workingDays,
                                    @RequestAttribute("user")User doctor){
        LOGGER.debug("values : {}",workingDays);
        if(isValidValues(workingDays)){
            userService.updateWorkingDays(doctor.getDoctor().getId(),workingDays);
        }
        return "redirect:/doctor/" + doctor.getId();
    }

    private boolean isValidValues(List<String> values){
        int count = 0;
        for (String value : values) {
            if(value != null && value.length() >= 4 && value.length() < 255){
                count++;
            }
        }
        return count == 7;
    }

    @PostMapping("/doctor/data/update")
    public String updateData(@ModelAttribute("form")@Valid UserUpdateRequestForm form, BindingResult result,
                             @RequestAttribute("user")User doctor,Model model){
        if(result.hasErrors()){
            model.addAttribute("doctor",pageService.getDoctor(doctor,doctor.getId()));
            model.addAttribute("departments",departmentService.getAll());
            return DOCTOR_PROFILE;
        }else if(!doctor.getUsername().equals(form.getUsername()) && userService.existsByUsername(form.getUsername())){
            result.addError(new FieldError("form","username",""));
            model.addAttribute("doctor",pageService.getDoctor(doctor,doctor.getId()));
            model.addAttribute("departments",departmentService.getAll());
            return DOCTOR_PROFILE;
        }else if(!passwordEncoder.matches(form.getOldPassword(),doctor.getPassword())){
            result.addError(new FieldError("form","oldPassword",""));
            model.addAttribute("doctor",pageService.getDoctor(doctor,doctor.getId()));
            model.addAttribute("departments",departmentService.getAll());
            return DOCTOR_PROFILE;
        }else {
            doctor.setName(form.getName());
            doctor.setSurname(form.getSurname());
            doctor.setUsername(form.getUsername());
            doctor.setPassword(passwordEncoder.encode(form.getNewPassword()));
            userService.updateDoctorData(doctor);
            return "redirect:/doctor/" + doctor.getId();
        }
    }

    @GetMapping({"/doctors","/admin/doctors"})
    public String doctors(Pageable pageable,Model model,@RequestAttribute("user")User user){
        int length = PageableUtil.getLength(
                userService.countByRole(UserRole.DOCTOR)-(user.getRole().equals(UserRole.DOCTOR) ? 1 : 0),
                DEFAULT_SIZE);
        pageable = PageableUtil.checkedPageable(pageable,length);
        model.addAttribute("doctors",userService.findAllByRole(UserRole.DOCTOR,pageable));
        model.addAttribute("page",pageable.getPageNumber());
        model.addAttribute("length",length);
        return DOCTORS;
    }

}
