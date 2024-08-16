package com.clicks.project_monitoring.repositories;

import com.clicks.project_monitoring.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByReference(String reference);

}
