package com.clicks.project_monitoring.utils;

import com.clicks.project_monitoring.dtos.response.CommentDto;
import com.clicks.project_monitoring.dtos.response.ProgressReportDto;
import com.clicks.project_monitoring.dtos.response.TaskDto;
import com.clicks.project_monitoring.dtos.response.user.UserDto;
import com.clicks.project_monitoring.model.Comment;
import com.clicks.project_monitoring.model.ProgressReport;
import com.clicks.project_monitoring.model.Task;
import com.clicks.project_monitoring.model.user.User;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class DtoMapper {

    public UserDto userToUserDto(User user) {
        return new UserDto(
                user.getReference(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name());
    }

    public CommentDto commentToCommentDto(Comment comment) {
        return new CommentDto(
                comment.getReference(),
                comment.getContent(),
                comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd : H:s")));
    }

    public ProgressReportDto progressReportDto(ProgressReport progressReport) {
        return new ProgressReportDto(
                progressReport.getReference(),
                progressReport.getTitle(),
                progressReport.getDescription()
        );
    }

    public TaskDto taskDto(Task task) {
        return new TaskDto(
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd : H:s")),
                task.getExpectedDeliveryDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd : H:s")));
    }
}
