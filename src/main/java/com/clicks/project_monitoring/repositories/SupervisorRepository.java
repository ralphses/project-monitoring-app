package com.clicks.project_monitoring.repositories;

import com.clicks.project_monitoring.model.user.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupervisorRepository extends JpaRepository<Supervisor, Long> {
    Optional<Supervisor> findByUserId(String userId);
    boolean existsByUserId(String userId);

}
