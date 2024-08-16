package com.clicks.project_monitoring.controllers;

import com.clicks.project_monitoring.dtos.requests.task.CreateTaskRequest;
import com.clicks.project_monitoring.dtos.response.AllTasks;
import com.clicks.project_monitoring.dtos.response.TaskDto;
import com.clicks.project_monitoring.service.TaskService;
import com.clicks.project_monitoring.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public CustomResponse getTasks(
            @RequestParam("page") int page,
            @RequestParam(value = "user", required = false) String user,
            @RequestParam(value = "progressStage") String progressStage
    ) {
        AllTasks tasks = taskService.getAll(page, user, progressStage);
        return new CustomResponse(true, tasks);
    }

    @GetMapping("{taskRef}")
    public CustomResponse getTasks(@PathVariable String taskRef) {
        TaskDto task = taskService.getOne(taskRef);
        return new CustomResponse(true, task);
    }

    @PutMapping("/{taskRef}/status")
    public CustomResponse updateStatus(
            @PathVariable String taskRef,
            @RequestParam(value = "status", required = false) String status) {
        String taskUpdated = taskService.updateStatus(taskRef, status);
        return new CustomResponse(true, taskUpdated);
    }

    @PostMapping("/{taskRef}/comments")
    public CustomResponse addComment(
            @PathVariable String taskRef, @RequestParam("comment") String comment) {
        TaskDto task = taskService.addComment(taskRef, comment);
        return new CustomResponse(true, task);
    }

    @PostMapping
    public CustomResponse create(@RequestBody CreateTaskRequest request) {
        String response = taskService.create(request);
        return new CustomResponse(true, response);
    }

    @PutMapping
    public CustomResponse changeStatus(@RequestParam("task") String task, @RequestParam("status") String status) {
        String response = taskService.changeStatus(task, status);
        return new CustomResponse(true, response);
    }
}
