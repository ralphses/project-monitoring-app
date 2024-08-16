package com.clicks.project_monitoring.dtos.response;

import java.util.List;

public record AllProjectResponse(int totalPages, long totalElements, List<ProjectDto> projects) {
}
