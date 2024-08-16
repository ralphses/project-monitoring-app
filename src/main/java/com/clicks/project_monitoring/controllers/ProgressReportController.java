package com.clicks.project_monitoring.controllers;

import com.clicks.project_monitoring.dtos.response.ProgressReportDto;
import com.clicks.project_monitoring.service.ProgressReportService;
import com.clicks.project_monitoring.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/progress-report")
public class ProgressReportController {

    private final ProgressReportService progressReportService;

    @GetMapping("{reference}")
    public CustomResponse getProgressReport(@PathVariable("reference") String reference) {
        ProgressReportDto report = progressReportService.getReport(reference);
        return new CustomResponse(true, report);
    }
}
