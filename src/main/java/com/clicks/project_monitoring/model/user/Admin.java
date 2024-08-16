package com.clicks.project_monitoring.model.user;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Admin extends User {


    public Admin(String name, String userReference) {
        super(userReference, name);
    }

    protected Admin() {}


}
