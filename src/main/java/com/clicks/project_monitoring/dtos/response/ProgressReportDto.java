package com.clicks.project_monitoring.dtos.response;

import java.util.List;

public record ProgressReportDto(
        String reference,
        List<ProgressReportStageDto> stages
) {
}
