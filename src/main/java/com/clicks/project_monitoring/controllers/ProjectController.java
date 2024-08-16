package com.clicks.project_monitoring.controllers;

import com.clicks.project_monitoring.dtos.response.AllProjectResponse;
import com.clicks.project_monitoring.service.ProjectService;
import com.clicks.project_monitoring.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public CustomResponse getAllProjects(@RequestParam(value = "page", defaultValue = "1") Integer page) {
        AllProjectResponse response = projectService.getAllProjects(page);
        return new CustomResponse(true, response);
    }

    @GetMapping("supervisor")
    public CustomResponse getAllSupervisorProjects(@RequestParam(value = "page", defaultValue = "1") Integer page) {
        AllProjectResponse response = projectService.getAllSupervisorProjects(page);
        return new CustomResponse(true, response);
    }

    @PutMapping("{studentMatric}")
    public CustomResponse uploadProjectFile(
            @PathVariable String studentMatric,
            @RequestParam(value = "file") MultipartFile file) {
        String response = projectService.upload(studentMatric, file);
        return new CustomResponse(true, response);
    }

    @GetMapping("file")
    public CustomResponse getProjectFile(@RequestParam(value = "project") String project) {
        AllProjectResponse response = projectService.getProjectFile(project);
        return new CustomResponse(true, response);
    }
}
