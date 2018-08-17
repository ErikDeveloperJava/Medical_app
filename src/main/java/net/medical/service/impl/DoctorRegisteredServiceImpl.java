package net.medical.service.impl;

import net.medical.model.DoctorRegistered;
import net.medical.model.RegisteredInfo;
import net.medical.model.enums.RegisteredStatus;
import net.medical.page.RequestDetail;
import net.medical.page.UserRequestPage;
import net.medical.repository.DoctorRegisteredRepository;
import net.medical.repository.RegisteredInfoRepository;
import net.medical.service.DoctorRegisteredService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class DoctorRegisteredServiceImpl implements DoctorRegisteredService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private DoctorRegisteredRepository registeredRepository;

    @Autowired
    private RegisteredInfoRepository registeredInfoRepository;

    @Override
    public int countByStatus(int doctorId,RegisteredStatus status) {
        return registeredRepository.countByDoctorIdAndStatus(doctorId,status);
    }

    @Transactional(rollbackFor = Exception.class)
    public UserRequestPage getByDoctorIdAndStatus(int doctorId, RegisteredStatus status) {
        List<RequestDetail> details = new ArrayList<>();
        for (DoctorRegistered registered : registeredRepository.findAllByDoctorIdAndStatus(doctorId, status)) {
            if(status.equals(RegisteredStatus.NEW)) {
                registered.setStatus(RegisteredStatus.UNCONFIRMED);
            }
            details.add(RequestDetail.builder()
                    .user(registered.getUser())
                    .registered(registered)
                    .build());
        }
        return UserRequestPage.builder()
                .details(details)
                .build();
    }

    @Transactional
    public void updateStatusById(RegisteredStatus status, int id) {
        DoctorRegistered one = registeredRepository.getOne(id);
        one.setStatus(status);
        LOGGER.debug("status updated");
    }

    @Transactional
    public void deleteById(int id) {
        registeredInfoRepository.deleteById(id);
        LOGGER.debug("doctorRegistered deleted");
    }
}
