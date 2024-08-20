package com.clicks.project_monitoring.dtos.requests.progress_stage;

import java.util.List;

public record CreateProjectStageRequest(
        String projectReference,
        List<StageAndTasks> stages) {
}
