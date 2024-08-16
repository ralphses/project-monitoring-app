package com.clicks.project_monitoring.dtos.response.notification;

public record NotificationDto(
        String message,
        String sender,
        String receiver,
        String timestamp
) {
}
