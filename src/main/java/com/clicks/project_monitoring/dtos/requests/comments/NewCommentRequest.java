package com.clicks.project_monitoring.dtos.requests.comments;

public record NewCommentRequest(String userReference, String taskReference, String comment) {
}
