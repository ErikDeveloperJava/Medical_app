package net.medical.repository;

import net.medical.model.Days;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DaysRepository extends JpaRepository<Days,Integer> {
}
