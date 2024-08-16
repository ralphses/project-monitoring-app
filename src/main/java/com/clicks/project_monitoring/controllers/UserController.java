package com.clicks.project_monitoring.controllers;

import com.clicks.project_monitoring.dtos.requests.user.AssignSupervisorRequest;
import com.clicks.project_monitoring.dtos.requests.user.NewSupervisorRequest;
import com.clicks.project_monitoring.dtos.response.user.AllStudentsResponse;
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
    public CustomResponse getUsers(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "type", defaultValue = "student") String type) {
        UsersResponse users = userService.getUsers(page, type);
        return new CustomResponse(true, users);
    }

    @GetMapping("students")
    public CustomResponse getStudents(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                      @RequestParam("supervisor") String supervisor) {
        AllStudentsResponse profile = userService.getStudents(page, supervisor);
        return new CustomResponse(true, profile);
    }

    @PostMapping("add-supervisor")
    public CustomResponse addSupervisor(@RequestBody NewSupervisorRequest request) {
        String profile = userService.addSupervisor(request);
        return new CustomResponse(true, profile);
    }

    @PostMapping("assign-supervisor")
    public CustomResponse assignSupervisor(@RequestBody AssignSupervisorRequest request) {
        String profile = userService.assignSupervisor(request);
        return new CustomResponse(true, profile);
    }
}
