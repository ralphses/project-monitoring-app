package com.clicks.project_monitoring.controllers;

import com.clicks.project_monitoring.dtos.response.user.UserDto;
import com.clicks.project_monitoring.dtos.response.user.UsersResponse;
import com.clicks.project_monitoring.service.UserService;
import com.clicks.project_monitoring.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public CustomResponse getUsers(@RequestParam("page") Integer page) {
        UsersResponse users = userService.getUsers(page);
        return new CustomResponse(true, users);
    }

    @GetMapping("{username}")
    public CustomResponse getUser(@PathVariable String username) {
        UserDto user = userService.getUser(username);
        return new CustomResponse(true, user);
    }
}
