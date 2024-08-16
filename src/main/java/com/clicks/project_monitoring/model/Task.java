package com.clicks.project_monitoring.model;

import com.clicks.project_monitoring.enums.EntityStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;
    private String stageReference;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime expectedDeliveryDate;

    @OneToMany(fetch = FetchType.LAZY)
    List<Comment> comments;
}
