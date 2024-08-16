package com.clicks.project_monitoring.service;

import com.clicks.project_monitoring.dtos.requests.auth.LoginRequest;
import com.clicks.project_monitoring.dtos.requests.auth.RegisterRequest;
import com.clicks.project_monitoring.dtos.response.user.AdminDto;
import com.clicks.project_monitoring.dtos.response.user.StudentDto;
import com.clicks.project_monitoring.exceptions.UnauthorizedUserException;
import com.clicks.project_monitoring.model.user.*;
import com.clicks.project_monitoring.utils.DtoMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final DtoMapper mapper;

    @Transactional
    public AdminDto login(LoginRequest loginRequest) {

        SecuredUser securedUser = userService.findByUsername(loginRequest.username());

        if (!securedUser.getPassword().equals(loginRequest.password())) {
            throw new UnauthorizedUserException("Invalid username or password");
        }

        return getUserDtoByRole(securedUser);
    }

    private AdminDto getUserDtoByRole(SecuredUser securedUser) {

        User user = userService.findUserByReference(securedUser.getReference(), securedUser.getRole());
        return switch (securedUser.getRole()) {
            case STUDENT -> mapper.studentDto((Student) user);
            case SUPERVISOR -> mapper.supervisorDto((Supervisor) user);
            case ADMIN -> mapper.adminDto((Admin) user);
        };
    }

    @Transactional
    public StudentDto register(RegisterRequest registerRequest) {
        Student student = userService.register(registerRequest);
        return mapper.studentDto(student);
    }

}
