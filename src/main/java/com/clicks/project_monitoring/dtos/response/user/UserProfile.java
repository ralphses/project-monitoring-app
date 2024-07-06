package com.clicks.project_monitoring.dtos.response.user;

import com.clicks.project_monitoring.dtos.response.CommentDto;
import com.clicks.project_monitoring.dtos.response.ProgressReportDto;
import com.clicks.project_monitoring.dtos.response.ProjectDto;
import com.clicks.project_monitoring.dtos.response.TaskDto;

import java.util.List;

public record UserProfile(
        UserDto user,
        List<ProjectDto> projects,
        List<TaskDto> tasks,
        List<CommentDto> comments,
        List<ProgressReportDto> progressReports,
        List<UserDto> students
) {
}
