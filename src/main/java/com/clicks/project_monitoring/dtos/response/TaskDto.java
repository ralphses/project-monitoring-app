package com.clicks.project_monitoring.dtos.response;

import com.clicks.project_monitoring.enums.EntityStatus;

import java.time.LocalDateTime;

public record TaskDto(
        String title,
        String description,
        String status,
        String createdAt,
        String expectedDeliveryDate
) {
}
