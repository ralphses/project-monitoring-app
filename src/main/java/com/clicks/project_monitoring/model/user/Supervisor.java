package com.clicks.project_monitoring.model.user;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@ToString

public class Supervisor extends User {

    @OneToMany(fetch = FetchType.LAZY)
    private List<Student> students;

    public Supervisor(String name, String userReference) {
        super(userReference, name);
        this.students = new ArrayList<>();
    }

    protected Supervisor() {}
}
