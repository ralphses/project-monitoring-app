package com.clicks.project_monitoring.dtos.response.user;

import com.clicks.project_monitoring.model.user.User;

import java.util.List;

public record UsersResponse(
        int noOfPages, long totalElements, List<UserDto> users
) {
}
