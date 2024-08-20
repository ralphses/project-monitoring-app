package com.clicks.project_monitoring.service;

import com.clicks.project_monitoring.dtos.requests.task.CreateTaskRequest;
import com.clicks.project_monitoring.dtos.requests.task.NewTask;
import com.clicks.project_monitoring.dtos.response.TaskDto;
import com.clicks.project_monitoring.enums.EntityStatus;
import com.clicks.project_monitoring.exceptions.InvalidParamException;
import com.clicks.project_monitoring.exceptions.ResourceNotFoundException;
import com.clicks.project_monitoring.model.ProgressReportStage;
import com.clicks.project_monitoring.model.Task;
import com.clicks.project_monitoring.repositories.TaskRepository;
import com.clicks.project_monitoring.utils.DtoMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProgressReportStageService progressReportStageService;
    private final DtoMapper mapper;


    @Transactional
    public String updateStatus(String taskRef, String status) {
        Task task = findByReference(taskRef);

        if (task == null) {
            throw new InvalidParamException("Invalid task project reference");
        }

        if (task.getStatus().equals(EntityStatus.COMPLETED)) {
            throw new InvalidParamException("Task already completed");
        }

        EntityStatus newStatus = getStatus(status);

        task.setStatus(newStatus);

        if (newStatus.equals(EntityStatus.COMPLETED)) {
            progressReportStageService.updateLevel(task.getStageReference());
        }

        return "Task updated successfully";
    }

    private EntityStatus getStatus(String status) {
        try {
            return EntityStatus.valueOf(status.toUpperCase().replace(" ", "_"));
        } catch (IllegalArgumentException e) {
            throw new InvalidParamException("Invalid status. Use any of " + Arrays.toString(EntityStatus.values()));
        }
    }

    public Task findByReference(String taskRef) {
        return taskRepository.findByReference(taskRef)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }


    @Transactional
    public String create(CreateTaskRequest request) {
        ProgressReportStage stage = progressReportStageService.getStage(request.stageReference());
        List<Task> savedTasks = request.tasks().stream()
                .map(newTask -> createNew(newTask, request.stageReference()))
                .toList();
        stage.getTasks().addAll(savedTasks);
        stage.incrementTaskCount(savedTasks.size());
        return "Task(s) added successfully";
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
            return LocalDateTime.parse(date);
        }catch (DateTimeParseException e) {
            throw new InvalidParamException("Invalid date: Use either yyyy-MM-dd or yyyy-MM-dd HH:mm:ss.");
        }
    }

    public boolean existByReference(String taskReference) {
        return taskRepository.existsByReference(taskReference);
    }

    public List<TaskDto> getTasksForStage(String stageReference) {
        ProgressReportStage stage = progressReportStageService.getStage(stageReference);
        return stage.getTasks().stream()
                .sorted(Comparator.comparing(Task::getCreatedAt).reversed())
                .map(mapper::taskToTaskDto)
                .toList();
    }

    public TaskDto getTask(String taskReference) {
        return mapper.taskToTaskDto(findByReference(taskReference));
    }
}
