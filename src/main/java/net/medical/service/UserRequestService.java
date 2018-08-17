package net.medical.service;

import net.medical.model.DoctorRegistered;
import net.medical.model.enums.RegisteredStatus;
import net.medical.page.UserRequestPage;

public interface UserRequestService {

    int countNEWorUNCONFIRMEDByUserId(int userId);

    int countByStatus(int userId,RegisteredStatus status);

    void add(DoctorRegistered registered);

    UserRequestPage getNewRequestsForm(int userId);

    void deleteById(int id);

    boolean existsByDoctorIdAndUserId(int doctorId, int userId);

    UserRequestPage getAcceptedRequestsForm(int userId);
}
