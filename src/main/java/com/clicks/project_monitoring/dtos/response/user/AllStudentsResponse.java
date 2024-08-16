package com.clicks.project_monitoring.dtos.response.user;

import java.util.List;

public record AllStudentsResponse(int totalPages, long totalElements, List<StudentDto> students) {
}
