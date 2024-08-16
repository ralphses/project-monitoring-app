package com.clicks.project_monitoring.service;

import com.clicks.project_monitoring.dtos.requests.progress_stage.ChangeProgressReportStageStatusRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressReportStageService {

    public String create(String project, List<String> stages) {
        return null;
    }

    public String changeStatus(ChangeProgressReportStageStatusRequest request) {
        return null;
    }
}
