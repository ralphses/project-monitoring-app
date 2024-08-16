package com.clicks.project_monitoring.dtos.response;

import java.util.List;

public record TaskDto(
        String reference,
        String title,
        String description,
        String status,
        String createdAt,
        String expectedDeliveryDate,
        List<CommentDto> comments
) {
}
