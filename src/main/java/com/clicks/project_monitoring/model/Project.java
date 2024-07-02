package com.clicks.project_monitoring.model;

import com.clicks.project_monitoring.enums.EntityStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    @OneToMany(fetch = FetchType.LAZY)
    List<Task> tasks;

    @OneToMany(fetch = FetchType.LAZY)
    List<ProgressReport> progressReports;

}
