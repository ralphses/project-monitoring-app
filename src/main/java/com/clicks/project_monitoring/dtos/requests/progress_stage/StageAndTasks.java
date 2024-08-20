package com.clicks.project_monitoring.dtos.requests.progress_stage;

import com.clicks.project_monitoring.dtos.requests.task.NewTask;

import java.util.List;

public record StageAndTasks(
        String stage,
        String stageExpectedCompletionDate,
        List<NewTask> tasks
) {
}
