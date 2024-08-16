package com.clicks.project_monitoring.controllers;

import com.clicks.project_monitoring.dtos.response.AllProjectResponse;
import com.clicks.project_monitoring.service.ProjectService;
import com.clicks.project_monitoring.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("{studentMatric}")
    public CustomResponse uploadProjectFile(
            @PathVariable String studentMatric,
            @RequestParam(value = "file") MultipartFile file) {
        String response = projectService.upload(studentMatric, file);
        return new CustomResponse(true, response);
    }


    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam("projectReference") String projectReference) {
        Resource resource = projectService.getProjectFile(projectReference);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
