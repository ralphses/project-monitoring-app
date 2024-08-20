package com.clicks.project_monitoring.controllers;

import com.clicks.project_monitoring.dtos.requests.progress_stage.ChangeProgressReportStageStatusRequest;
import com.clicks.project_monitoring.dtos.requests.progress_stage.CreateProjectStageRequest;
import com.clicks.project_monitoring.service.ProgressReportStageService;
import com.clicks.project_monitoring.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/progress-report/stages")
public class ProgressReportStageController {

    private final ProgressReportStageService progressReportStageService;

    @PostMapping
    public CustomResponse create(@RequestBody CreateProjectStageRequest request) {
        String response = progressReportStageService.create(request);
        return new CustomResponse(true, response);
    }

    @GetMapping("level")
    public CustomResponse getLevel(@RequestParam("stageReference")String stageReference) {
        Integer response = progressReportStageService.getLevel(stageReference);
        return new CustomResponse(true, response);
    }

    @PutMapping
    public CustomResponse changeStatus(@RequestBody ChangeProgressReportStageStatusRequest request) {
        String response = progressReportStageService.changeStatus(request);
        return new CustomResponse(true, response);
    }
}
