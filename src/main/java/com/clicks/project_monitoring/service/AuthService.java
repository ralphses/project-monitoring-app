package com.clicks.project_monitoring.service;

import com.clicks.project_monitoring.dtos.requests.auth.LoginRequest;
import com.clicks.project_monitoring.dtos.requests.auth.RegisterRequest;
import com.clicks.project_monitoring.dtos.response.user.UserDto;
import com.clicks.project_monitoring.dtos.response.user.UserProfile;
import com.clicks.project_monitoring.enums.UserRole;
import com.clicks.project_monitoring.exceptions.UnauthorizedUserException;
import com.clicks.project_monitoring.model.user.Student;
import com.clicks.project_monitoring.model.user.User;
import com.clicks.project_monitoring.utils.DtoMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final DtoMapper mapper;

    public UserProfile login(LoginRequest loginRequest) {

        User user = userService.findByUsername(loginRequest.username());

        if (user.getPassword().equals(loginRequest.password())) {

            UserDto userDto = mapper.userToUserDto(user);
            return new UserProfile(userDto);
        }
        throw new UnauthorizedUserException("Invalid username or password");
    }

    @Transactional
    public UserProfile register(RegisterRequest registerRequest) {
        User registeredUser = userService.register(registerRequest);
        return new UserProfile(mapper.userToUserDto(registeredUser));
    }
}
