package net.medical.controller;

import net.medical.form.DoctorRegisteredRequestForm;
import net.medical.form.DoctorRegisteredResponseForm;
import net.medical.model.DoctorRegistered;
import net.medical.model.RegisteredInfo;
import net.medical.model.User;
import net.medical.model.enums.RegisteredStatus;
import net.medical.page.Pages;
import net.medical.service.UserRequestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/user/request")
public class UserRequestController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserRequestService userRequestService;


    @PostMapping("/new/count")
    public @ResponseBody
    int newRequestsCount(@RequestAttribute("user") User user) {
        return userRequestService.countNEWorUNCONFIRMEDByUserId(user.getId());
    }

    @PostMapping("/accepted/count")
    public @ResponseBody
    int acceptedRequestsCount(@RequestAttribute("user") User user) {
        return userRequestService.countByStatus(user.getId(), RegisteredStatus.ACCEPTED);
    }

    @PostMapping("/add")
    public @ResponseBody
    DoctorRegisteredResponseForm add(@Valid DoctorRegisteredRequestForm form, BindingResult result,
                                     @RequestAttribute("user")User user) {
        LOGGER.debug("form : {}, error count : {}",form,result.getFieldErrorCount());
        Date date;
        if (userRequestService.existsByDoctorIdAndUserId(form.getDoctorId(),user.getId())){
            return DoctorRegisteredResponseForm.builder()
                    .exists(true)
                    .build();
        }else if(result.hasErrors()){
            DoctorRegisteredResponseForm responseForm = new DoctorRegisteredResponseForm();
            for (FieldError error : result.getFieldErrors()) {
                responseForm.setTitleError(error.getField().endsWith("title"));
                responseForm.setMessageError(error.getField().endsWith("message"));
            }
            return responseForm;
        }else if((date = getValidDate(form.getDate())) == null){
            return DoctorRegisteredResponseForm.builder()
                    .dateError(true)
                    .build();
        }else {
            DoctorRegistered registered = DoctorRegistered.builder()
                    .doctor(User.builder().id(form.getDoctorId()).build())
                    .status(RegisteredStatus.NEW)
                    .user(User.builder().id(user.getId()).build())
                    .info(RegisteredInfo.builder()
                            .date(date)
                            .title(form.getTitle())
                            .message(form.getMessage())
                            .build())
                    .build();
            userRequestService.add(registered);
            return DoctorRegisteredResponseForm.builder()
                    .success(true)
                    .build();
        }
    }

    private Date getValidDate(String strDate){
        String[] array = strDate.split("T");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date registeredDate = dateFormat.parse(array[0] + " " + array[1]);
            Date currentDate = new Date();
            if(registeredDate.compareTo(currentDate) <= 0){
                return null;
            }else {
                return registeredDate;
            }
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @GetMapping("/new")
    public String newRequests(@RequestAttribute("user")User user, Model model){
        model.addAttribute("requestPage",userRequestService.getNewRequestsForm(user.getId()));
        model.addAttribute("userRequest",true);
        return REGISTERED;
    }

    @PostMapping("/delete")
    public @ResponseBody
    boolean delete(@RequestParam("registeredId")int registeredId,@RequestAttribute("user")User user){
        LOGGER.debug("registeredId : {}",registeredId);
        userRequestService.deleteById(registeredId);
        return true;
    }

    @GetMapping("/accepted")
    public String acceptedRequests(@RequestAttribute("user")User user,Model model){
        model.addAttribute("requestPage",userRequestService.getAcceptedRequestsForm(user.getId()));
        model.addAttribute("userRequest",true);
        return REGISTERED;
    }
}
