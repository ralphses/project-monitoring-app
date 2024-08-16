package com.clicks.project_monitoring.dtos.response;

import java.util.List;

public record ProgressReportStageDto(
        String reference,
        String name,
        boolean completed,
        String completionDate,
        int level,
        List<TaskDto> tasks
) {
}
