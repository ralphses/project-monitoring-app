package com.clicks.project_monitoring.model.user;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Supervisor extends User{

    @OneToMany(fetch = FetchType.LAZY)
    private List<Student> students;
}
