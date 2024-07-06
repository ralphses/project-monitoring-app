package com.clicks.project_monitoring.dtos.response;

public record CommentDto(
        String reference,
        String content,
        String createdAt
) {
}
