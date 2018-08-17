package net.medical.controller;

import net.medical.model.User;
import net.medical.model.enums.RegisteredStatus;
import net.medical.page.Pages;
import net.medical.service.DoctorRegisteredService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/doctor/users")
public class DoctorRegisteredController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private DoctorRegisteredService doctorRegisteredService;

    @PostMapping("/new/count")
    public @ResponseBody
    int newRegisteredUsersCount(@RequestAttribute("user")User doctor){
        return doctorRegisteredService.countByStatus(doctor.getId(),RegisteredStatus.NEW);
    }

    @PostMapping("/accepted/count")
    public @ResponseBody
    int acceptedRegisteredUsersCount(@RequestAttribute("user")User doctor){
        return doctorRegisteredService.countByStatus(doctor.getId(),RegisteredStatus.ACCEPTED);
    }

    @PostMapping("/unconfirmed/count")
    public @ResponseBody
    int unconfirmedRegisteredUsersCount(@RequestAttribute("user")User doctor){
        return doctorRegisteredService.countByStatus(doctor.getId(),RegisteredStatus.UNCONFIRMED);
    }

    @GetMapping("/new")
    public String newAllRegistered(@RequestAttribute("user")User doctor, Model model){
        model.addAttribute("requestPage",doctorRegisteredService.getByDoctorIdAndStatus(doctor.getId(),RegisteredStatus.NEW));
        return REGISTERED;
    }

    @PostMapping("/accepted")
    public @ResponseBody
    boolean acceptedRegistered(@RequestParam("registeredId")int registeredId){
        LOGGER.debug("registeredId : {}",registeredId);
        doctorRegisteredService.updateStatusById(RegisteredStatus.ACCEPTED,registeredId);
        return true;
    }

    @PostMapping("/delete")
    public @ResponseBody
    boolean deleteRegistered(@RequestParam("registeredId")int registeredId){
        LOGGER.debug("registeredId :{}",registeredId);
        doctorRegisteredService.deleteById(registeredId);
        return true;
    }

    @GetMapping("/accepted")
    public String acceptedAllRegistered(@RequestAttribute("user")User doctor,Model model){
        model.addAttribute("requestPage",doctorRegisteredService.getByDoctorIdAndStatus(doctor.getId(),RegisteredStatus.ACCEPTED));
        return REGISTERED;
    }

    @GetMapping("/unconfirmed")
    public String unconfirmedAllRegistered(@RequestAttribute("user")User doctor,Model model){
        model.addAttribute("requestPage",doctorRegisteredService.getByDoctorIdAndStatus(doctor.getId(),RegisteredStatus.UNCONFIRMED));
        return REGISTERED;
    }
}
