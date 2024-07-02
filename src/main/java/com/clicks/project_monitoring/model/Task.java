package com.clicks.project_monitoring.model;

import com.clicks.project_monitoring.enums.EntityStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;
    private String title;
    private String description;
    private EntityStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime expectedDeliveryDate;
}
