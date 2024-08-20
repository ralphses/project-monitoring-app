package com.clicks.project_monitoring.dtos.response;

import com.clicks.project_monitoring.dtos.response.user.SupervisorDto;

import java.util.List;

public record AllSupervisorResponse(int totalPages, long totalElements, List<SupervisorDto> supervisors) {
}
