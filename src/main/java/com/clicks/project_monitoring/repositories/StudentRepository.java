package com.clicks.project_monitoring.repositories;

import com.clicks.project_monitoring.model.user.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUserId(String userId);
    Optional<Student> findByMatric(String matric);
    Page<Student> findAllBySupervisor(String supervisorId, Pageable pageable);

}
