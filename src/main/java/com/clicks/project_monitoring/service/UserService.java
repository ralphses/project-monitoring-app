package com.clicks.project_monitoring.service;

import com.clicks.project_monitoring.dtos.requests.auth.RegisterRequest;
import com.clicks.project_monitoring.dtos.requests.user.AssignSupervisorRequest;
import com.clicks.project_monitoring.dtos.requests.user.NewSupervisorRequest;
import com.clicks.project_monitoring.dtos.response.AllSupervisorResponse;
import com.clicks.project_monitoring.dtos.response.user.AdminDto;
import com.clicks.project_monitoring.dtos.response.user.AllStudentsResponse;
import com.clicks.project_monitoring.dtos.response.user.StudentDto;
import com.clicks.project_monitoring.dtos.response.user.UsersResponse;
import com.clicks.project_monitoring.enums.UserRole;
import com.clicks.project_monitoring.exceptions.InvalidParamException;
import com.clicks.project_monitoring.exceptions.ResourceExistsException;
import com.clicks.project_monitoring.exceptions.ResourceNotFoundException;
import com.clicks.project_monitoring.model.user.SecuredUser;
import com.clicks.project_monitoring.model.user.Student;
import com.clicks.project_monitoring.model.user.Supervisor;
import com.clicks.project_monitoring.model.user.User;
import com.clicks.project_monitoring.repositories.AdminRepository;
import com.clicks.project_monitoring.repositories.StudentRepository;
import com.clicks.project_monitoring.repositories.SupervisorRepository;
import com.clicks.project_monitoring.repositories.UserRepository;
import com.clicks.project_monitoring.utils.DtoMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

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

            Student student = new Student(registerRequest.name(), savedSecuredUser.getReference(), savedSecuredUser.getUsername());
            return studentRepository.save(student);
        }
        throw new ResourceExistsException("Username already exists");
    }


    public AllStudentsResponse getStudents(Integer page, String supervisor) {
        Page<Student> studentPage = studentRepository
                .findAllBySupervisor(supervisor, PageRequest.of(Math.max(0, (page - 1)), 10));

        return new AllStudentsResponse(
                studentPage.getTotalPages(),
                studentPage.getTotalElements(),
                studentPage.map(mapper::studentDto).toList());
    }

    public String addSupervisor(NewSupervisorRequest request) {
        if(!userRepository.existsByUsername(request.userName())) {
            SecuredUser user = new SecuredUser(request.userName(), request.password(), UserRole.SUPERVISOR);
            SecuredUser savedUser = userRepository.save(user);

            Supervisor newSupervisor = new Supervisor(request.name(), savedUser.getReference());
            supervisorRepository.save(newSupervisor);
        }
        return "Supervisor added successfully";
    }

    @Transactional
    public String assignSupervisor(AssignSupervisorRequest request) {
        System.out.println("request = " + request);
        Student student = studentRepository.findByMatric(request.student())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        if (Objects.nonNull(student.getSupervisor())) {
            Supervisor supervisor = supervisorRepository.findByUserId(student.getSupervisor())
                    .orElseThrow(() -> new ResourceNotFoundException("Supervisor not found"));
            supervisor.getStudents().remove(student);
        }
        Supervisor supervisor = supervisorRepository.findByUserId(request.supervisor())
                .orElseThrow(() -> new ResourceNotFoundException("Supervisor not found"));
        supervisor.getStudents().add(student);
        student.setSupervisor(request.supervisor());
        return "Supervisor assigned successfully";
    }

    public Student getStudent(String studentReference) {
        return studentRepository.findByUserId(studentReference)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }

    public SecuredUser findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public String findUserName(String userReference) {
        return userRepository.findUserName(userReference);
    }

    public Student findStudent(String reference) {
        return studentRepository.findByUserId(reference)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }

    public AllSupervisorResponse getSupervisors(Integer page) {
        Page<Supervisor> supervisorPage = supervisorRepository.findAll(PageRequest.of(Math.max(0, (page-1)), 10));
        return new AllSupervisorResponse(
                supervisorPage.getTotalPages(),
                supervisorPage.getTotalElements(),
                supervisorPage.map(mapper::supervisorDto).toList());
    }

    public StudentDto getStudentByMatric(String matric) {
        Student student = studentRepository.findByMatric(matric).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        return mapper.studentDto(student);
    }
}
