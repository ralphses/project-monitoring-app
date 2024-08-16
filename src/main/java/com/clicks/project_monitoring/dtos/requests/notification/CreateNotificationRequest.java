package com.clicks.project_monitoring.dtos.requests.notification;

public record CreateNotificationRequest(
        String message,
        String recipient,
        String sender
) {
}
