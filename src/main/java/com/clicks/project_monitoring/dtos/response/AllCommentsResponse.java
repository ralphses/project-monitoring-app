package com.clicks.project_monitoring.dtos.response;

import java.util.List;

public record AllCommentsResponse(int totalPages, long totalElements, List<CommentDto> comments) {
}
