package com.clicks.project_monitoring.repositories;

import com.clicks.project_monitoring.model.ProgressReportStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgressReportStageRepository extends JpaRepository<ProgressReportStage, Long> {
}
