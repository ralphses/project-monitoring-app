package com.clicks.project_monitoring.service;

import com.clicks.project_monitoring.dtos.response.AllProjectResponse;
import com.clicks.project_monitoring.model.Project;
import com.clicks.project_monitoring.model.user.Student;
import com.clicks.project_monitoring.repositories.ProjectRepository;
import com.clicks.project_monitoring.utils.DtoMapper;
import com.clicks.project_monitoring.utils.FileStorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.module.ResolutionException;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final FileStorageService fileStorageService;
    private final UserService userService;
    private final DtoMapper mapper;

    public AllProjectResponse getAllProjects(Integer page) {
        Page<Project> projects = projectRepository.findAll(PageRequest.of(Math.max(0, (page-1)), 10));
        return new AllProjectResponse(
                projects.getTotalPages(),
                projects.getTotalElements(),
                projects.map(mapper::projectDto).toList()
        );
    }

    @Transactional
    public String upload(String studentMatric, MultipartFile file) {
        String fileName = fileStorageService.upload(studentMatric, file);
        Student student = userService.getStudent(studentMatric);
        student.getProject().setProjectFile(fileName);
        return "Project file uploaded successfully";
    }

    public Project getProjectByReference(String reference) {
        return projectRepository.findByReference(reference)
                .orElseThrow(() -> new ResolutionException("Project not found"));
    }

    public Resource getProjectFile(String projectReference) {
        return fileStorageService.loadFileAsResource(projectReference);
    }
}
