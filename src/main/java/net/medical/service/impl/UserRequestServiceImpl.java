package net.medical.service.impl;

import net.medical.model.DoctorRegistered;
import net.medical.model.enums.RegisteredStatus;
import net.medical.page.RequestDetail;
import net.medical.page.UserRequestPage;
import net.medical.repository.DoctorRegisteredRepository;
import net.medical.repository.RegisteredInfoRepository;
import net.medical.service.UserRequestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserRequestServiceImpl implements UserRequestService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private DoctorRegisteredRepository doctorRegisteredRepository;

    @Autowired
    private RegisteredInfoRepository registeredInfoRepository;

    @Override
    public int countNEWorUNCONFIRMEDByUserId(int userId) {
        return doctorRegisteredRepository.countNEWorUNCONFIRMEDByUserId(userId);
    }

    @Override
    public int countByStatus(int userId, RegisteredStatus status) {
        return doctorRegisteredRepository.countByUserIdAndStatus(userId,status);
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(DoctorRegistered registered) {
        doctorRegisteredRepository.save(registered);
        LOGGER.debug("DoctorRegistered  saved");
    }

    @Override
    public UserRequestPage getNewRequestsForm(int userId) {
        List<DoctorRegistered> list = doctorRegisteredRepository.findAllByNEWAndUNCONFIRMED(userId);
        List<RequestDetail> details = new ArrayList<>();
        for (DoctorRegistered registered : list) {
            details.add(RequestDetail.builder()
                    .user(registered.getDoctor())
                    .registered(registered)
                    .build());
        }
        return UserRequestPage.builder()
                .details(details)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(int id) {
        registeredInfoRepository.deleteById(id);
        LOGGER.debug("DoctorRegistered request deleted");
    }

    @Override
    public boolean existsByDoctorIdAndUserId(int doctorId, int userId) {
        return doctorRegisteredRepository.existsByDoctorIdAndUserId(doctorId,userId);
    }

    @Override
    public UserRequestPage getAcceptedRequestsForm(int userId) {
        List<DoctorRegistered> list = doctorRegisteredRepository.findAllByACCEPTEDAndUserId(userId);
        List<RequestDetail> details = new ArrayList<>();
        for (DoctorRegistered registered : list) {
            details.add(RequestDetail.builder()
                    .user(registered.getDoctor())
                    .registered(registered)
                    .build());
        }
        return UserRequestPage.builder()
                .details(details)
                .build();
    }

}
