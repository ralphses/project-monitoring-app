package com.clicks.project_monitoring.dtos.response;

public record ProjectDto(
        String reference,
        String userReference,
        String title,
        String description,
        String status,
        String projectFile,
        ProgressReportDto progressReport
) {
}
