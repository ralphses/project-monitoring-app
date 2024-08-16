package com.clicks.project_monitoring.service;

import com.clicks.project_monitoring.dtos.response.ProgressReportDto;
import com.clicks.project_monitoring.repositories.ProgressReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProgressReportService {

    private final ProgressReportRepository progressReportRepository;

    public ProgressReportDto getReport(String reference) {
        return null;
    }
}
