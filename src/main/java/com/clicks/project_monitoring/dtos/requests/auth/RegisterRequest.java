package com.clicks.project_monitoring.dtos.requests.auth;

public record RegisterRequest(
        String username,
        String name,
        String email,
        String password
) {
}
