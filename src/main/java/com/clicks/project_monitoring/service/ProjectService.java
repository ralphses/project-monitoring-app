package com.clicks.project_monitoring.service;

import com.clicks.project_monitoring.dtos.requests.project.CreateProjectRequest;
import com.clicks.project_monitoring.dtos.response.AllProjectResponse;
import com.clicks.project_monitoring.dtos.response.LatestComment;
import com.clicks.project_monitoring.dtos.response.LatestTask;
import com.clicks.project_monitoring.dtos.response.ProjectDto;
import com.clicks.project_monitoring.dtos.response.user.StudentDto;
import com.clicks.project_monitoring.enums.EntityStatus;
import com.clicks.project_monitoring.exceptions.InvalidParamException;
import com.clicks.project_monitoring.model.*;
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
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final FileStorageService fileStorageService;
    private final UserService userService;
    private final DtoMapper mapper;

    public AllProjectResponse getAllProjects(Integer page) {
        Page<Project> projects = projectRepository.findAll(PageRequest.of(Math.max(0, (page - 1)), 10));
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
        String projectFile = getProjectByReference(projectReference).getProjectFile();
        return fileStorageService.loadFileAsResource(projectFile);
    }

    public ProjectDto getStudentProject(String reference) {
        Student student = userService.findStudent(reference);
        return mapper.projectDto(student.getProject());
    }

    @Transactional
    public StudentDto create(CreateProjectRequest request) {
        Student student = userService.findStudent(request.userReference());

        Project project = projectRepository.save(Project.builder()
                .status(EntityStatus.INITIATED)
                .description(request.description())
                .title(request.title())
                .reference(UUID.randomUUID().toString())
                .userReference(student.getUserId())
                .build());
        student.setProject(project);

        return mapper.studentDto(student);
    }

    @Transactional
    public LatestTask getLatest(String projectReference) {
        Project project = getProjectByReference(projectReference);
        ProgressReport progressReport = project.getProgressReport();
        if (Objects.nonNull(progressReport)) {
            List<ProgressReportStage> stages = progressReport.getStages();
            if (!stages.isEmpty()) {
                stages = stages.stream().filter(st -> !st.getTasks().isEmpty()).collect(Collectors.toList());
                stages.sort(Comparator.comparing(ProgressReportStage::getCreatedAt).reversed());
                ProgressReportStage stage = stages.get(0);
                List<Task> tasks = stage.getTasks();
                if (!tasks.isEmpty()) {
                    tasks.sort(Comparator.comparing(Task::getCreatedAt).reversed());
                    Task task = tasks.get(0);
                    return new LatestTask(task.getTitle() + " (" + task.getDescription() + ")", stage.getReference(), task.getStatus().name());
                }

            }
        }
        return null;
    }

    public LatestComment getLatestComment(String projectReference) {
        Project project = getProjectByReference(projectReference);
        ProgressReport progressReport = project.getProgressReport();
        if (Objects.nonNull(progressReport)) {
            List<ProgressReportStage> stages = progressReport.getStages();
            if (!stages.isEmpty()) {
                stages = stages.stream().filter(st -> !st.getTasks().isEmpty()).collect(Collectors.toList());
                stages.sort(Comparator.comparing(ProgressReportStage::getCreatedAt).reversed());
                ProgressReportStage stage = stages.get(0);
                List<Task> tasks = stage.getTasks();
                if (!tasks.isEmpty()) {
                    tasks.sort(Comparator.comparing(Task::getCreatedAt).reversed());
                    Task task = tasks.get(0);
                    List<Comment> comments = task.getComments();
                    comments.sort(Comparator.comparing(Comment::getCreatedAt).reversed());
                    if (!comments.isEmpty()) {
                        Comment comment = comments.get(0);
                        return new LatestComment(comment.getContent(), task.getReference());
                    }
                }

            }
        }
        return null;
    }

    @Transactional
    public String updateStatus(String projectReference, String status) {
        Project projectByReference = getProjectByReference(projectReference);
        projectByReference.setStatus(getStatus(status));
        return "Project status updated successfully";
    }

    private EntityStatus getStatus(String status) {
        try {
            return EntityStatus.valueOf(status.toUpperCase().replace(" ", "_").trim());
        }catch (IllegalArgumentException e){
            throw new InvalidParamException("Invalid status " + status);
        }
    }
}
