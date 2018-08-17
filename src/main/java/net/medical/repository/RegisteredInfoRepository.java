package net.medical.repository;

import net.medical.model.RegisteredInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisteredInfoRepository extends JpaRepository<RegisteredInfo,Integer> {

}
