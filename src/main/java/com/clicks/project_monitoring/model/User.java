package com.clicks.project_monitoring.model;

import com.clicks.project_monitoring.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reference;
    private String name;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;
}
