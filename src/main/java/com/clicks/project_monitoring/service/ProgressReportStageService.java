package com.clicks.project_monitoring.service;

import com.clicks.project_monitoring.dtos.requests.progress_stage.ChangeProgressReportStageStatusRequest;
import com.clicks.project_monitoring.dtos.requests.progress_stage.CreateProjectStageRequest;
import com.clicks.project_monitoring.dtos.requests.progress_stage.StageAndTasks;
import com.clicks.project_monitoring.dtos.requests.task.NewTask;
import com.clicks.project_monitoring.enums.EntityStatus;
import com.clicks.project_monitoring.exceptions.InvalidParamException;
import com.clicks.project_monitoring.exceptions.ResourceNotFoundException;
import com.clicks.project_monitoring.model.ProgressReport;
import com.clicks.project_monitoring.model.ProgressReportStage;
import com.clicks.project_monitoring.model.Project;
import com.clicks.project_monitoring.model.Task;
import com.clicks.project_monitoring.repositories.ProgressReportStageRepository;
import com.clicks.project_monitoring.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.clicks.project_monitoring.enums.EntityStatus.COMPLETED;

@Service
@RequiredArgsConstructor
public class ProgressReportStageService {

    private final ProgressReportStageRepository progressReportStageRepository;
    private final ProjectService projectService;
    private final ProgressReportService progressReportService;
    private final TaskRepository taskRepository;

    @Transactional
    public String create(CreateProjectStageRequest request) {

        String projectReference = request.projectReference();
        Project project = projectService.getProjectByReference(projectReference);

        // Map the stages and tasks to create them
        List<ProgressReportStage> savedStages = request.stages().stream()
                .map(this::createStageWithTasks)
                .collect(Collectors.toList());

        // Create the progress report and associate it with the project
        ProgressReport progressReport = progressReportService.create(savedStages, projectReference);
        project.setProgressReport(progressReport);

        return "Project progress report created with stages";
    }

    private ProgressReportStage createStageWithTasks(StageAndTasks stageAndTasks) {

        // Create a new stage
        ProgressReportStage newStage = createNewStage(stageAndTasks.stage(), parseDate(stageAndTasks.stageExpectedCompletionDate()));

        // Create and add tasks to the new stage
        List<Task> tasks = stageAndTasks.tasks().stream()
                .map(newTask -> createNew(newTask, newStage.getReference()))
                .toList();

        newStage.getTasks().addAll(tasks);
        System.out.println("newStage = " + newStage.getTasks().size());
        return newStage;
    }

    public Task createNew(NewTask newTask, String stageReference) {
        Task task = Task.builder()
                .stageReference(stageReference)
                .reference(UUID.randomUUID().toString())
                .description(newTask.description())
                .createdAt(LocalDateTime.now())
                .title(newTask.title())
                .comments(new ArrayList<>())
                .expectedDeliveryDate(parseDate(newTask.expectedCompletionDate()))
                .status(EntityStatus.INITIATED)
                .build();
        return taskRepository.save(task);
    }

    private LocalDateTime parseDate(String date) {
        try {
            return LocalDate.parse(date).atTime(LocalTime.now());
        }catch (DateTimeParseException e) {
            throw new InvalidParamException("Invalid date: Use either yyyy-MM-dd or yyyy-MM-dd HH:mm:ss.");
        }
    }


    private ProgressReportStage createNewStage(String stage, LocalDateTime expectedCompletionDate) {
        ProgressReportStage progressReportStage = ProgressReportStage.builder()
                .tasks(new ArrayList<>())
                .name(stage)
                .createdAt(LocalDateTime.now())
                .completionDate(expectedCompletionDate)
                .reference(UUID.randomUUID().toString())
                .build();
        return progressReportStageRepository.save(progressReportStage);
    }

    @Transactional
    public String changeStatus(ChangeProgressReportStageStatusRequest request) {
        ProgressReportStage progressReportStage = getStage(request.reference());
        EntityStatus status = getStatus(request.newStatus());
        progressReportStage.setCompleted(status.equals(COMPLETED));
        progressReportStage.setLevel(progressReportStage.getTasks().size());
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

    public boolean exists(String reference) {
        return progressReportStageRepository.existsByReference(reference);
    }


    public Integer getLevel(String stageReference) {
        ProgressReportStage stage = getStage(stageReference);
        int size = stage.getTasks().size();
        if (size == 0) {
            return 0;
        }
        long count = stage.getTasks().stream()
                .filter(task -> task.getStatus() == COMPLETED)
                .count();

        long percentageCompleted = (count * 100) / size;
        return Math.toIntExact(percentageCompleted);
    }
}
