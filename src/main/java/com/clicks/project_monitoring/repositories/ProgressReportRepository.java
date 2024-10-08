package com.clicks.project_monitoring.repositories;

import com.clicks.project_monitoring.model.ProgressReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgressReportRepository extends JpaRepository<ProgressReport, Long> {
}
