package com.clicks.project_monitoring.controllers;

import com.clicks.project_monitoring.dtos.requests.notification.CreateNotificationRequest;
import com.clicks.project_monitoring.dtos.response.notification.NotificationDto;
import com.clicks.project_monitoring.service.NotificationService;
import com.clicks.project_monitoring.utils.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public CustomResponse createNotification(@RequestBody CreateNotificationRequest request) {
        String response = notificationService.create(request);
        return new CustomResponse(true, response);
    }

    @GetMapping
    public CustomResponse createNotification(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "user") String user) {
        List<NotificationDto> notifications = notificationService.getAll(page, user);
        return new CustomResponse(true, notifications);
    }
}
