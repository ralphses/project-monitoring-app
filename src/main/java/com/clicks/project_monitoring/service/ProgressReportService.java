package com.clicks.project_monitoring.service;

import com.clicks.project_monitoring.dtos.response.ProgressReportDto;
import com.clicks.project_monitoring.model.ProgressReport;
import com.clicks.project_monitoring.model.ProgressReportStage;
import com.clicks.project_monitoring.model.Project;
import com.clicks.project_monitoring.repositories.ProgressReportRepository;
import com.clicks.project_monitoring.utils.DtoMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProgressReportService {

    private final ProgressReportRepository progressReportRepository;
    private final ProjectService projectService;
    private final DtoMapper mapper;

    @Transactional
    public ProgressReportDto getReport(String reference) {
        Project project = projectService.getProjectByReference(reference);
        ProgressReport progressReport = project.getProgressReport();
        return mapper.progressReportDto(progressReport);
    }

    public ProgressReport create(List<ProgressReportStage> stages, String projectReference) {

        Project project = projectService.getProjectByReference(projectReference);

        ProgressReport progressReport = Optional.ofNullable(project.getProgressReport())
                .orElseGet(() -> {
                    ProgressReport newReport = ProgressReport.builder()
                            .reference(UUID.randomUUID().toString())
                            .stages(new ArrayList<>())
                            .build();
                    project.setProgressReport(newReport);
                    return progressReportRepository.save(newReport);
                });

        progressReport.getStages().addAll(stages);
        return progressReportRepository.save(progressReport);
    }


}
