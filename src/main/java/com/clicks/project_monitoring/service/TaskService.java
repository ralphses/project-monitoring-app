package com.clicks.project_monitoring.service;

import com.clicks.project_monitoring.dtos.requests.task.CreateTaskRequest;
import com.clicks.project_monitoring.dtos.response.AllTasks;
import com.clicks.project_monitoring.dtos.response.TaskDto;
import com.clicks.project_monitoring.enums.EntityStatus;
import com.clicks.project_monitoring.exceptions.InvalidParamException;
import com.clicks.project_monitoring.exceptions.ResourceNotFoundException;
import com.clicks.project_monitoring.model.Task;
import com.clicks.project_monitoring.repositories.TaskRepository;
import com.clicks.project_monitoring.utils.DtoMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final DtoMapper mapper;
    private final CommentService commentService;


    public AllTasks getAll(int page, String userReference, String progressStage) {

//        PageRequest pageRequest = PageRequest.of(Math.max(0, page - 1), 10);
//        Page<Task> taskPage;
//
//        if (Objects.nonNull(userReference)) {
//            User user = userService.findByUserReference(userReference);
//
//            if (user instanceof Student) {
//                taskPage = taskRepository.findAllByUserReferenceOrderByCreatedAtDesc(pageRequest, userReference);
//            } else if (user instanceof Supervisor supervisor) {
//                List<String> studentReferences = supervisor.getStudents().stream()
//                        .map(User::getReference)
//                        .collect(Collectors.toList());
//                taskPage = taskRepository.findAllByUserReferenceIn(studentReferences, pageRequest);
//            } else {
//                throw new UnauthorizedUserException("Not authorized user");
//            }
//        } else {
//            taskPage = taskRepository.findAll(pageRequest);
//        }

//        return new AllTasks(
//                taskPage.getTotalPages(),
//                taskPage.getTotalElements(),
//                taskPage.getContent().stream()
//                        .map(mapper::taskToTaskDto)
//                        .collect(Collectors.toList())
//        );
        return null;
    }

    @Transactional
    public String updateStatus(String taskRef, String status) {
        Task task = findByReference(taskRef);

        if (task == null) {
            throw new InvalidParamException("Invalid task reference");
        }

        try {
            EntityStatus taskStatus = EntityStatus.valueOf(status.toUpperCase().replace(" ", "_"));
            task.setStatus(taskStatus);
            return status;
        } catch (IllegalArgumentException e) {
            throw new InvalidParamException("Invalid status. Use any of " + Arrays.toString(EntityStatus.values()));
        }
    }

    public Task findByReference(String taskRef) {
        return taskRepository.findByReference(taskRef)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    public TaskDto getOne(String taskRef) {
//        return mapper.taskToTaskDto(findByReference(taskRef));
        return null;
    }

    public TaskDto addComment(String taskRef, String comment) {

        return null;
    }

    public String create(CreateTaskRequest request) {
        return null;
    }

    public String changeStatus(String task, String status) {
        return null;
    }
}
