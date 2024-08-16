package com.clicks.project_monitoring.dtos.response;

import java.util.List;

public record AllTasks(
        int noOfPages, long totalElements, List<TaskDto> tasks
) {
}
