package net.medical.controller;

import net.medical.model.Department;
import net.medical.page.Pages;
import net.medical.service.DepartmentService;
import net.medical.service.UserService;
import net.medical.util.PageableUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DepartmentController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final int DEFAULT_SIZE = 4;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;

    @PostMapping("/departments")
    public String departments(Model model){
        model.addAttribute("departments",departmentService.getAll());
        return DEPARTMENTS;
    }

    @PostMapping("/admin/departments/select")
    public String departmentsSelect(Model model){
        model.addAttribute("departments",departmentService.getAll());
        return DEPARTMENTS_SELECT;
    }

    @GetMapping("/department/{id}")
    public String department(@PathVariable("id")String strId, Pageable pageable,
                             Model model){
        LOGGER.debug("id : {}",strId);
        int id = getId(strId);
        Department department;
        if(id == -1 || (department = departmentService.getById(id)) == null){
            return "redirect:/";
        }else {
            int length = PageableUtil.getLength(userService.countDoctorsByDepartment(id),DEFAULT_SIZE);
            pageable = PageableUtil.checkedPageable(pageable,length);
            model.addAttribute("doctors",userService.getDoctorsByDepartment(id,pageable));
            model.addAttribute("length",length);
            model.addAttribute("page",pageable.getPageNumber());
            model.addAttribute("department",department);
            return DOCTORS;
        }
    }

    private int getId(String strId){
        int id;
        try {
            id = Integer.parseInt(strId);
            if(id <= 0){
                id = -1;
            }
        }catch (NumberFormatException e){
            id = -1;
        }
        return id;
    }
}
