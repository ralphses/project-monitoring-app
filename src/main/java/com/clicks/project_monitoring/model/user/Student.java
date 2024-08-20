package com.clicks.project_monitoring.model.user;

import com.clicks.project_monitoring.model.Project;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Student extends User {

    @OneToOne(fetch = FetchType.LAZY)
    private Project project;
    private String supervisor;
    private String matric;

    public Student(String name, String userReference, String matric) {
        super(userReference, name);
        this.matric = matric;
    }
}
