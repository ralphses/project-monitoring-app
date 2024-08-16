package com.clicks.project_monitoring.service;

import com.clicks.project_monitoring.dtos.requests.auth.RegisterRequest;
import com.clicks.project_monitoring.dtos.requests.user.AssignSupervisorRequest;
import com.clicks.project_monitoring.dtos.requests.user.NewSupervisorRequest;
import com.clicks.project_monitoring.dtos.response.user.AdminDto;
import com.clicks.project_monitoring.dtos.response.user.AllStudentsResponse;
import com.clicks.project_monitoring.dtos.response.user.UsersResponse;
import com.clicks.project_monitoring.enums.UserRole;
import com.clicks.project_monitoring.exceptions.InvalidParamException;
import com.clicks.project_monitoring.exceptions.ResourceExistsException;
import com.clicks.project_monitoring.exceptions.ResourceNotFoundException;
import com.clicks.project_monitoring.model.user.SecuredUser;
import com.clicks.project_monitoring.model.user.Student;
import com.clicks.project_monitoring.model.user.User;
import com.clicks.project_monitoring.repositories.AdminRepository;
import com.clicks.project_monitoring.repositories.StudentRepository;
import com.clicks.project_monitoring.repositories.SupervisorRepository;
import com.clicks.project_monitoring.repositories.UserRepository;
import com.clicks.project_monitoring.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;
    private final SupervisorRepository supervisorRepository;

    private final DtoMapper mapper;

    public User findUserByReference(String userReference, UserRole role) {
        return switch (role) {
            case ADMIN -> adminRepository.findByUserId(userReference)
                    .orElseThrow(() -> new ResourceNotFoundException("Admin User not found"));
            case STUDENT -> studentRepository.findByUserId(userReference)
                    .orElseThrow(() -> new ResourceNotFoundException("Student User not found"));
            case SUPERVISOR -> supervisorRepository.findByUserId(userReference)
                    .orElseThrow(() -> new ResourceNotFoundException("Supervisor User not found"));
        };
    }

    public UsersResponse getUsers(Integer page, String type) {

        UserRole role = getUserRole(type);
        Pageable pageable = PageRequest.of(Math.max(0, page - 1), 10);

        Page<AdminDto> allUsers = switch (role) {
            case ADMIN -> adminRepository.findAll(pageable).map(mapper::adminDto);
            case STUDENT -> studentRepository.findAll(pageable).map(mapper::studentDto);
            case SUPERVISOR -> supervisorRepository.findAll(pageable).map(mapper::supervisorDto);
        };

        return new UsersResponse(allUsers.getTotalPages(), allUsers.getTotalElements(), allUsers.stream().toList());

    }

    private UserRole getUserRole(String type) {
        try {
            return UserRole.valueOf(type.trim().toUpperCase().replace(" ", "_"));
        } catch (IllegalArgumentException e) {
            throw new InvalidParamException("Invalid user type");
        }
    }

    public boolean checkStudentExist(String matric) {
        return userRepository.existsByUsername(matric);
    }

    public Student register(RegisterRequest registerRequest) {

        if (!checkStudentExist(registerRequest.username())) {

            SecuredUser newUser = new SecuredUser(registerRequest.username(), registerRequest.password(), UserRole.STUDENT);
            SecuredUser savedSecuredUser = userRepository.save(newUser);

            Student student = new Student(registerRequest.name(), savedSecuredUser.getReference());
            return studentRepository.save(student);
        }
        throw new ResourceExistsException("Username already exists");
    }


    public AllStudentsResponse getStudents(Integer page, String supervisor) {
        return null;
    }

    public String addSupervisor(NewSupervisorRequest request) {
        return null;
    }

    public String assignSupervisor(AssignSupervisorRequest request) {
        return null;
    }

    public SecuredUser findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
