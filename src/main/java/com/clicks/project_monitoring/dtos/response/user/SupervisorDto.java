package com.clicks.project_monitoring.dtos.response.user;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class SupervisorDto extends AdminDto {

    private final List<StudentDto> students;

    public SupervisorDto(String reference, String name, String email, String role, List<StudentDto> students) {
        super(reference, name, email, role);
        this.students = students;
    }
}
