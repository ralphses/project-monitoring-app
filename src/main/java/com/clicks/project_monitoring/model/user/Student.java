package com.clicks.project_monitoring.model.user;

import com.clicks.project_monitoring.model.Project;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student extends User {

    @OneToOne(fetch = FetchType.LAZY)
    private Project project;
}
