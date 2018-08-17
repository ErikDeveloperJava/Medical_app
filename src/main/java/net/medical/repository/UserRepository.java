package net.medical.repository;

import net.medical.model.User;
import net.medical.model.enums.UserRole;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("select u from User u where u.role='DOCTOR' order by size(u.doctors) desc")
    List<User> find8BestDoctors(Pageable pageable);

    List<User> findAllByRole(UserRole role);

    List<User> findAllByRole(UserRole role,Pageable pageable);

    boolean existsByIdAndRole(int id,UserRole role);

    @Query("select u from User u where u.role='DOCTOR' and u.id != :doctorId order by size(u.doctors) desc")
    List<User> findAllDoctors(@Param("doctorId") int doctorId, Pageable pageable);

    int countByRole(UserRole role);

    @Query("select u from User u where u.doctor.department.id=:depId and u.role='DOCTOR'")
    List<User> findAllDoctorsByDepartmentId(@Param("depId") int depId,Pageable pageable);

    @Query("select count(*) from User u where u.doctor.department.id=:depId and u.role='DOCTOR'")
    int countAllDoctorsByDepartmentId(@Param("depId") int depId);
}
