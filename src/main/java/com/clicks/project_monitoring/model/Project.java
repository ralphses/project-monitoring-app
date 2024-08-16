package com.clicks.project_monitoring.model;

import com.clicks.project_monitoring.enums.EntityStatus;
import jakarta.persistence.*;
import lombok.*;

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
    private String userReference;

    private String title;
    private String description;
    private String projectFile;

    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    @OneToOne
    private ProgressReport progressReport;

}
