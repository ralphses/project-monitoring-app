package com.clicks.project_monitoring.controllers;

import com.clicks.project_monitoring.dtos.requests.project.CreateProjectRequest;
import com.clicks.project_monitoring.dtos.response.AllProjectResponse;
import com.clicks.project_monitoring.dtos.response.LatestComment;
import com.clicks.project_monitoring.dtos.response.LatestTask;
import com.clicks.project_monitoring.dtos.response.ProjectDto;
import com.clicks.project_monitoring.dtos.response.user.StudentDto;
import com.clicks.project_monitoring.service.ProjectService;
import com.clicks.project_monitoring.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public CustomResponse createProject(@RequestBody CreateProjectRequest request) {
        StudentDto response = projectService.create(request);
        return new CustomResponse(true, response);
    }

    @GetMapping
    public CustomResponse getAllProjects(@RequestParam(value = "page", defaultValue = "1") Integer page) {
        AllProjectResponse response = projectService.getAllProjects(page);
        return new CustomResponse(true, response);
    }

    @GetMapping("student")
    public CustomResponse getProject(@RequestParam(value = "reference") String reference) {
        ProjectDto project = projectService.getStudentProject(reference);
        return new CustomResponse(true, project);
    }

    @PutMapping("{studentReference}")
    public CustomResponse uploadProjectFile(
            @PathVariable String studentReference,
            @RequestParam(value = "file") MultipartFile file) {
        String response = projectService.upload(studentReference, file);
        return new CustomResponse(true, response);
    }


    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam("projectReference") String projectReference) {
        Resource resource = projectService.getProjectFile(projectReference);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("latest-task")
    public CustomResponse getLatestTask(@RequestParam("projectReference") String projectReference) {
        LatestTask latestTask = projectService.getLatest(projectReference);
        return new CustomResponse(true, latestTask);
    }

    @GetMapping("latest-comment")
    public CustomResponse getLatestComment(@RequestParam("projectReference") String projectReference) {
        LatestComment latestComment = projectService.getLatestComment(projectReference);
        return new CustomResponse(true, latestComment);
    }

    @PutMapping("update-status")
    public CustomResponse updateStatus(@RequestParam("projectReference") String projectReference, @RequestParam("newStatus") String status) {
        String response = projectService.updateStatus(projectReference, status);
        return new CustomResponse(true, response);
    }
}
