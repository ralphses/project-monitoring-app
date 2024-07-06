package com.clicks.project_monitoring.dtos.requests.auth;

public record LoginRequest(
        String username,
        String password
) {
}
