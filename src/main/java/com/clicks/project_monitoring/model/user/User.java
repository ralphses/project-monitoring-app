package com.clicks.project_monitoring.model.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@MappedSuperclass
@NoArgsConstructor
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String userId;
    protected String name;
    protected String phone;
    protected String email;

    @ElementCollection
    @CollectionTable(name = "user_notifications")
    protected Set<String> notifications;

    public User(String userReference, String name) {
        this.notifications = new HashSet<>();
        this.userId = userReference;
        this.name = name;
    }
}
