package com.clicks.project_monitoring.dtos.requests.task;

public record NewTask(
        String title,
        String description,
        String expectedCompletionDate
) {
}
