package com.clicks.project_monitoring.dtos.response.user;

import com.clicks.project_monitoring.dtos.response.ProjectDto;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class StudentDto extends AdminDto {

    private final ProjectDto project;
    private final String supervisor;

    public StudentDto(String reference, String name,  String email, String role, ProjectDto project, String supervisor) {
        super(reference, name, email, role);
        this.project = project;
        this.supervisor = supervisor;
    }
}
