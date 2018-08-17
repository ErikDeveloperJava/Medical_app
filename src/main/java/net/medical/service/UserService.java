package net.medical.service;

import net.medical.model.Doctor;
import net.medical.model.User;
import net.medical.model.WorkingDays;
import net.medical.model.enums.UserRole;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    void add(User user, MultipartFile img);

    boolean existsByUsername(String username);

    List<User> findAllByRole(UserRole role);

    List<User> findAllByRole(UserRole role, Pageable pageable);

    void addDoctor(User user, Doctor doctor, List<WorkingDays> workingDays,MultipartFile image);

    boolean existsByIdAndRole(int id,UserRole role);

    void updateWorkingDays(int doctorId,List<String> values);

    void updateDoctorData(User doctor);

    int countByRole(UserRole role);

    List<User> getDoctorsByDepartment(int depId,Pageable pageable);

    int countDoctorsByDepartment(int depId);

    User deleteById(int id);
}
