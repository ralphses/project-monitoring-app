package com.clicks.project_monitoring.dtos.requests.user;

public record NewSupervisorRequest(
        String name,
        String userName,
        String password
) {
}
