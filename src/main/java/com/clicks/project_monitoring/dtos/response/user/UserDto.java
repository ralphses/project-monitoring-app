package com.clicks.project_monitoring.dtos.response.user;

public record UserDto(
        String reference,
        String name,
        String username,
        String email,
        String role
) {}
