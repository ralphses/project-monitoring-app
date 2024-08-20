package com.clicks.project_monitoring.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProgressReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reference;

    @OneToMany(fetch = FetchType.LAZY)
    private List<ProgressReportStage> stages;

}
