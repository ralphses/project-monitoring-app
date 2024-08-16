package com.clicks.project_monitoring.utils;

import com.clicks.project_monitoring.dtos.response.*;
import com.clicks.project_monitoring.dtos.response.user.AdminDto;
import com.clicks.project_monitoring.dtos.response.user.StudentDto;
import com.clicks.project_monitoring.dtos.response.user.SupervisorDto;
import com.clicks.project_monitoring.enums.UserRole;
import com.clicks.project_monitoring.model.*;
import com.clicks.project_monitoring.model.user.Admin;
import com.clicks.project_monitoring.model.user.Student;
import com.clicks.project_monitoring.model.user.Supervisor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
public class DtoMapper {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd:h:sa");

    public AdminDto adminDto(Admin admin) {
        return new AdminDto(
                admin.getUserId(),
                admin.getName(),
                admin.getEmail(),
                UserRole.ADMIN.name()
        );
    }

    public SupervisorDto supervisorDto(Supervisor supervisor) {
        return new SupervisorDto(
                supervisor.getUserId(),
                supervisor.getName(),
                supervisor.getEmail(),
                UserRole.SUPERVISOR.name(),
                supervisor.getStudents().stream().map(this::studentDto).toList()
        );
    }

    public StudentDto studentDto(Student student) {
        return new StudentDto(
                student.getUserId(),
                student.getName(),
                student.getEmail(),
                UserRole.STUDENT.name(),
                Objects.nonNull(student.getProject()) ? projectDto(student.getProject()) : null,
                student.getSupervisor()
        );
    }

    public ProjectDto projectDto(Project project) {
        return new ProjectDto(
                project.getReference(),
                project.getUserReference(),
                project.getTitle(),
                project.getDescription(),
                project.getStatus().name()
        );

    }

    public ProgressReportDto progressReportDto(ProgressReport progressReport) {
        return new ProgressReportDto(
                progressReport.getReference(),
                progressReport.getStages().stream()
                        .map(this::progressReportStageDto)
                        .toList()
        );
    }

    public ProgressReportStageDto progressReportStageDto(ProgressReportStage progressReportStage) {
        return new ProgressReportStageDto(
                progressReportStage.getReference(),
                progressReportStage.getName(),
                progressReportStage.isCompleted(),
                progressReportStage.getCompletionDate().format(DATE_FORMATTER),
                progressReportStage.getLevel(),
                progressReportStage.getTasks().stream()
                        .map(this::taskToTaskDto)
                        .toList()
        );
    }


    //    public UserDto userToUserDto(User user) {
//        if (user instanceof Student student) {
//            StudentDto studentDto = studentToStudentDto(student);
//            System.out.println("studentDto = " + studentDto);
//            return studentDto;
//        } else if (user instanceof Supervisor supervisor) {
//            return supervisorToSupervisorDto(supervisor);
//        } else {
//            return userToUserDtoBasic(user);
//        }
//    }
//
//    private static UserDto userToUserDtoBasic(User user) {
//        return new UserDto(
//                user.getReference(),
//                user.getName(),
//                user.getUsername(),
//                user.getEmail(),
//                user.getRole().name());
//    }
//
//    private SupervisorDto supervisorToSupervisorDto(Supervisor supervisor) {
//        return new SupervisorDto(
//                supervisor.getReference(),
//                supervisor.getName(),
//                supervisor.getUsername(),
//                supervisor.getEmail(),
//                supervisor.getRole().name(),
//                supervisor.getStudents().stream()
//                        .map(this::studentToStudentDto)
//                        .toList());
//    }
//
//    private StudentDto studentToStudentDto(Student student) {
//
//        Project project = student.getProject();
//
//        return new StudentDto(
//                student.getReference(),
//                student.getName(),
//                student.getUsername(),
//                student.getEmail(),
//                student.getRole().name(),
//                project.getTitle(),
//                project.getComments().stream()
//                        .sorted(Comparator.comparing(Comment::getCreatedAt))
//                        .map(Comment::getContent)
//                        .findFirst()
//                        .orElse(""),
//                project.getTasks().stream()
//                        .sorted(Comparator.comparing(Task::getCreatedAt))
//                        .map(this::taskToTaskDto)
//                        .findFirst()
//                        .orElse(null),
//                project.getProgressReports().stream()
//                        .sorted(Comparator.comparing(ProgressReport::getCreatedAt))
//                        .map(this::progressReportToProgressReportDto)
//                        .findAny()
//                        .orElse(null));
//    }
//
    public CommentDto commentToCommentDto(Comment comment) {
        return new CommentDto(
                comment.getReference(),
                comment.getContent(),
                comment.getCreatedAt().format(DATE_FORMATTER));
    }

    //
//    public ProgressReportDto progressReportToProgressReportDto(ProgressReport progressReport) {
//        return new ProgressReportDto(
//                progressReport.getReference(),
//                progressReport.getTitle(),
//                progressReport.getDescription());
//    }
//
    public TaskDto taskToTaskDto(Task task) {
        return new TaskDto(
                task.getReference(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getCreatedAt().format(DATE_FORMATTER),
                task.getExpectedDeliveryDate().format(DATE_FORMATTER),
                task.getComments().stream()
                        .map(this::commentToCommentDto)
                        .toList());
    }
//
//    public ProjectDto projectToProjectDto(Project project) {
//        return new ProjectDto(
//                project.getReference(),
//                project.getUserReference(),
//                project.getTitle(),
//                project.getDescription(),
//                project.getStatus().name(),
//                project.getComments().stream()
//                        .sorted(Comparator.comparing(Comment::getCreatedAt))
//                        .map(Comment::getContent)
//                        .findFirst()
//                        .orElse(""));
//    }
}
