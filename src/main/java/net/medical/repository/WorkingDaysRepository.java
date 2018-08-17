package net.medical.repository;

import net.medical.model.WorkingDays;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkingDaysRepository extends JpaRepository<WorkingDays,Integer> {

    List<WorkingDays> findAllByDoctorId(int doctorId);
}
