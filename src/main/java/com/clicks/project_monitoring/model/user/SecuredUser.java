package com.clicks.project_monitoring.model.user;

import com.clicks.project_monitoring.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class SecuredUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reference;
    
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public SecuredUser(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.reference = UUID.randomUUID().toString();
    }
}
