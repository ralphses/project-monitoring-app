package com.clicks.project_monitoring.repositories;

import com.clicks.project_monitoring.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

//    Page<Task> findAllByUserReferenceOrderByCreatedAtDesc(Pageable pageable, String userReference);
//    Page<Task> findAllByUserReferenceIn(List<String> references, Pageable pageable);

    Optional<Task> findByReference(String reference);
}
