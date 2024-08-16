package com.clicks.project_monitoring.controllers;

import com.clicks.project_monitoring.dtos.requests.progress_stage.ChangeProgressReportStageStatusRequest;
import com.clicks.project_monitoring.service.ProgressReportStageService;
import com.clicks.project_monitoring.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/progress-report/stages")
public class ProgressReportStageController {

    private final ProgressReportStageService progressReportStageService;

    @PostMapping
    public CustomResponse create(
            @RequestParam("projectReference") String project,
            @RequestParam("stages") List<String> stages) {
        String response = progressReportStageService.create(project, stages);
        return new CustomResponse(true, response);
    }

    @PutMapping
    public CustomResponse changeStatus(@RequestBody ChangeProgressReportStageStatusRequest request) {
        String response = progressReportStageService.changeStatus(request);
        return new CustomResponse(true, response);
    }
}
