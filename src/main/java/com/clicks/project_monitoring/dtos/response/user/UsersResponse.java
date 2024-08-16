package com.clicks.project_monitoring.dtos.response.user;

import java.util.List;

public record UsersResponse(
        int noOfPages, long totalElements, List<? extends AdminDto> users
) {
}
