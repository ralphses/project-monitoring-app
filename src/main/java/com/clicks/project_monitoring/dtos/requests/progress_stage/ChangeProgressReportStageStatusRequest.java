package com.clicks.project_monitoring.dtos.requests.progress_stage;

public record ChangeProgressReportStageStatusRequest(
        String reference,
        String newStatus
) {
}
