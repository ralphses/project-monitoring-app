package com.clicks.project_monitoring.service;

import com.clicks.project_monitoring.dtos.requests.progress_stage.ChangeProgressReportStageStatusRequest;
import com.clicks.project_monitoring.enums.EntityStatus;
import com.clicks.project_monitoring.exceptions.InvalidParamException;
import com.clicks.project_monitoring.exceptions.ResourceNotFoundException;
import com.clicks.project_monitoring.model.ProgressReport;
import com.clicks.project_monitoring.model.ProgressReportStage;
import com.clicks.project_monitoring.model.Project;
import com.clicks.project_monitoring.repositories.ProgressReportStageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.clicks.project_monitoring.enums.EntityStatus.COMPLETED;

@Service
@RequiredArgsConstructor
public class ProgressReportStageService {

    private final ProgressReportStageRepository progressReportStageRepository;
    private final ProjectService projectService;
    private final ProgressReportService progressReportService;

    @Transactional
    public String create(String projectReference, List<String> stages) {
        Project project = projectService.getProjectByReference(projectReference);
        List<ProgressReportStage> savedStages = stages.stream().map(this::createNewStage).toList();
        ProgressReport progressReport = progressReportService.create(savedStages, projectReference);
        project.setProgressReport(progressReport);
        return "Project progress report created with stages";
    }

    private ProgressReportStage createNewStage(String stage) {
        ProgressReportStage progressReportStage = ProgressReportStage.builder()
                .tasks(new ArrayList<>())
                .name(stage)
                .reference(UUID.randomUUID().toString())
                .build();
        return progressReportStageRepository.save(progressReportStage);
    }

    @Transactional
    public String changeStatus(ChangeProgressReportStageStatusRequest request) {
        ProgressReportStage progressReportStage = getStage(request.reference());
        EntityStatus status = getStatus(request.newStatus());
        progressReportStage.setCompleted(status.equals(COMPLETED));
        return "Stage completed successfully";
    }

    private EntityStatus getStatus(String status) {
        try {
            return EntityStatus.valueOf(status.replace(" ", "_").toUpperCase());
        }catch (IllegalArgumentException exception) {
            throw new InvalidParamException("Invalid status: " + status);
        }
    }

    public ProgressReportStage getStage(String stageReference) {
        return progressReportStageRepository.findByReference(stageReference)
                .orElseThrow(() -> new ResourceNotFoundException("Progress Report stage not found"));
    }

    public void updateLevel(String stageReference) {
        ProgressReportStage stage = getStage(stageReference);
        stage.setLevel(stage.getLevel() + 1);
    }
}
