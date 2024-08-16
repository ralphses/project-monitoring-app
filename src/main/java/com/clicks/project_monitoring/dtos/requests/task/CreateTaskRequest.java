package com.clicks.project_monitoring.dtos.requests.task;

import java.util.List;

public record CreateTaskRequest(
        String stageReference,
        List<NewTask> tasks
) {
}
