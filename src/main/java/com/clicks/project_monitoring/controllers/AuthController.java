package com.clicks.project_monitoring.controllers;

import com.clicks.project_monitoring.dtos.requests.auth.LoginRequest;
import com.clicks.project_monitoring.dtos.requests.auth.RegisterRequest;
import com.clicks.project_monitoring.dtos.response.user.UserProfile;
import com.clicks.project_monitoring.service.AuthService;
import com.clicks.project_monitoring.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    public CustomResponse login(@RequestBody LoginRequest loginRequest) {
        UserProfile profile = authService.login(loginRequest);
        return new CustomResponse(true, profile);
    }

    @PostMapping("register")
    public CustomResponse register(@RequestBody RegisterRequest registerRequest) {
        UserProfile profile = authService.register(registerRequest);
        return new CustomResponse(true, profile);
    }
}
