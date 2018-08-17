package net.medical.service;

import net.medical.model.enums.RegisteredStatus;
import net.medical.page.UserRequestPage;

public interface DoctorRegisteredService {

    int countByStatus(int doctorId,RegisteredStatus status);

    UserRequestPage getByDoctorIdAndStatus(int doctorId,RegisteredStatus status);

    void updateStatusById(RegisteredStatus status,int id);

    void deleteById(int id);
}
