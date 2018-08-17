package net.medical.service.impl;

import net.medical.model.Doctor;
import net.medical.model.User;
import net.medical.model.WorkingDays;
import net.medical.model.enums.UserRole;
import net.medical.repository.DoctorRepository;
import net.medical.repository.UserRepository;
import net.medical.repository.WorkingDaysRepository;
import net.medical.service.UserService;
import net.medical.util.ImageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private WorkingDaysRepository workingDaysRepository;

    @Autowired
    private ImageUtil imageUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    public void add(User user, MultipartFile img) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        String imgName = System.currentTimeMillis() + img.getOriginalFilename();
        try {
            imageUtil.save(String.valueOf(user.getId()),imgName,img.getBytes());
            user.setImgUrl(user.getId() + "/" + imgName);
            LOGGER.debug("user saved");
        }catch (Exception e){
            imageUtil.delete(String.valueOf(user.getId()));
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public List<User> findAllByRole(UserRole role) {
        return userRepository.findAllByRole(role);
    }

    @Override
    public List<User> findAllByRole(UserRole role, Pageable pageable) {
        return userRepository.findAllByRole(role,pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addDoctor(User user, Doctor doctor, List<WorkingDays> workingDays, MultipartFile image) {
        doctor = doctorRepository.save(doctor);
        String imgName = System.currentTimeMillis() + image.getOriginalFilename();
        user.setDoctor(doctor);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        for (WorkingDays workingDay : workingDays) {
            workingDay.setDoctor(doctor);
        }
        user = userRepository.save(user);
        user.setImgUrl(user.getId() + "/" + imgName);
        workingDaysRepository.saveAll(workingDays);
        try {
            imageUtil.save(String.valueOf(user.getId()),imgName,image.getBytes());
            LOGGER.debug("doctor saved");
        }catch (Exception e){
            imageUtil.delete(String.valueOf(user.getId()));
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByIdAndRole(int id,UserRole role) {
        return userRepository.existsByIdAndRole(id,role);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateWorkingDays(int doctorId, List<String> values) {
        List<WorkingDays> workingDays = workingDaysRepository.findAllByDoctorId(doctorId);
        for (int i = 0; i < workingDays.size(); i++) {
            workingDays.get(i).setWorkingHours(values.get(i));
        }
        LOGGER.debug("working days updated");
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateDoctorData(User doctor) {
        userRepository.save(doctor);
        LOGGER.debug("doctor updated");
    }

    @Override
    public int countByRole(UserRole role) {
        return userRepository.countByRole(role);
    }

    @Override
    public List<User> getDoctorsByDepartment(int depId, Pageable pageable) {
        return userRepository.findAllDoctorsByDepartmentId(depId,pageable);
    }

    @Override
    public int countDoctorsByDepartment(int depId) {
        return userRepository.countAllDoctorsByDepartmentId(depId);
    }

    @Transactional
    public User deleteById(int id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            userRepository.delete(user.get());
            return user.get();
        }
        return null;
    }

}
