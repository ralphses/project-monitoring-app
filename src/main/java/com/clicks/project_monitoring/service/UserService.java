package com.clicks.project_monitoring.service;

import com.clicks.project_monitoring.dtos.requests.auth.RegisterRequest;
import com.clicks.project_monitoring.dtos.response.CommentDto;
import com.clicks.project_monitoring.dtos.response.ProgressReportDto;
import com.clicks.project_monitoring.dtos.response.TaskDto;
import com.clicks.project_monitoring.dtos.response.user.UserDto;
import com.clicks.project_monitoring.dtos.response.user.UserProfile;
import com.clicks.project_monitoring.dtos.response.user.UsersResponse;
import com.clicks.project_monitoring.enums.UserRole;
import com.clicks.project_monitoring.exceptions.ResourceExistsException;
import com.clicks.project_monitoring.exceptions.ResourceNotFoundException;
import com.clicks.project_monitoring.model.Comment;
import com.clicks.project_monitoring.model.ProgressReport;
import com.clicks.project_monitoring.model.Project;
import com.clicks.project_monitoring.model.Task;
import com.clicks.project_monitoring.model.user.Student;
import com.clicks.project_monitoring.model.user.User;
import com.clicks.project_monitoring.repositories.UserRepository;
import com.clicks.project_monitoring.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final DtoMapper mapper;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UsersResponse getUsers(Integer page) {
        Pageable pageable = PageRequest.of(Math.max(0, page-1), 10);
        Page<User> allUsers = userRepository.findAll(pageable);
        List<UserDto> users = allUsers
                .stream()
                .map(mapper::userToUserDto)
                .toList();

        return new UsersResponse(allUsers.getTotalPages(), allUsers.getTotalElements(), users);

    }

    public boolean checkExist(String username) {
        return userRepository.existsByUsername(username);
    }

    public User register(RegisterRequest registerRequest) {
        if(!checkExist(registerRequest.username())) {
            Student student = new Student();
            student.setUsername(registerRequest.username());
            student.setReference(UUID.randomUUID().toString());
            student.setPassword(registerRequest.password());
            student.setEmail(registerRequest.email());
            student.setRole(UserRole.STUDENT);
            student.setComments(new ArrayList<>());
            student.setName(registerRequest.name());

            return userRepository.save(student);
        }
        throw new ResourceExistsException("Username already exists");
    }

    public UserProfile getUser(String username) {
        User user = findByUsername(username);
        if(user instanceof Student) {
            Student student = (Student) user;

            Project studentProject = student.getProject();

            List<TaskDto> tasks = studentProject.getTasks()
                    .stream()
                    .map(mapper::taskDto)
                    .toList();

            List<ProgressReportDto> progressReports = studentProject.getProgressReports()
                    .stream()
                    .map(mapper::progressReportDto)
                    .toList();

            List<CommentDto> comments = user.getComments()
                    .stream()
                    .map(mapper::commentToCommentDto)
                    .toList();

        }
        return null;
    }
}
