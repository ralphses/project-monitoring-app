package com.clicks.project_monitoring.controllers;

import com.clicks.project_monitoring.dtos.requests.task.CreateTaskRequest;
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

    @PutMapping("/{taskRef}/status")
    public CustomResponse updateStatus(
            @PathVariable String taskRef,
            @RequestParam(value = "status", required = false) String status) {
        String taskUpdated = taskService.updateStatus(taskRef, status);
        return new CustomResponse(true, taskUpdated);
    }

    @PostMapping
    public CustomResponse create(@RequestBody CreateTaskRequest request) {
        String response = taskService.create(request);
        return new CustomResponse(true, response);
    }

    @PutMapping
    public CustomResponse changeStatus(@RequestParam("task") String task, @RequestParam("status") String status) {
        String response = taskService.updateStatus(task, status);
        return new CustomResponse(true, response);
    }
}
