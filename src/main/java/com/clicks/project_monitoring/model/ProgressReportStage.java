package com.clicks.project_monitoring.model;

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
public class ProgressReportStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;
    private String name;
    private boolean completed;
    private LocalDateTime completionDate;
    private int level;
    private int totalTasks;

    @OneToMany(fetch = FetchType.LAZY)
    List<Task> tasks;

    public void incrementTaskCount(int size) {
        this.totalTasks = this.totalTasks + size;
    }
}
