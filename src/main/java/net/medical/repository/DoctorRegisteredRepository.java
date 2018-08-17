package net.medical.repository;

import net.medical.model.DoctorRegistered;
import net.medical.model.enums.RegisteredStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorRegisteredRepository extends JpaRepository<DoctorRegistered,Integer> {

    int countByDoctorIdAndStatus(int doctorId,RegisteredStatus status);

    @Query("select count(*) from DoctorRegistered d where d.user.id=:userId and (status='NEW' or status='UNCONFIRMED')")
    int countNEWorUNCONFIRMEDByUserId(@Param("userId") int userId);

    int countByUserIdAndStatus(int userId,RegisteredStatus status);

    @Query("select d from DoctorRegistered d where (d.status='NEW' or d.status='UNCONFIRMED') and d.user.id=:userId")
    List<DoctorRegistered> findAllByNEWAndUNCONFIRMED(@Param("userId")int userId);

    boolean existsByDoctorIdAndUserId(int doctorId,int userId);

    @Query("select count(*) from DoctorRegistered  d where (d.status='NEW' or d.status='UNCONFIRMED') and d.user.id=:userId")
    int countByNewOrUnconfirmedAndUserId(@Param("userId") int userId);

    @Query("select d from DoctorRegistered d where d.status='ACCEPTED' and d.user.id=:userId")
    List<DoctorRegistered> findAllByACCEPTEDAndUserId(@Param("userId") int userId);

    List<DoctorRegistered> findAllByDoctorIdAndStatus(int doctorId,RegisteredStatus status);
}