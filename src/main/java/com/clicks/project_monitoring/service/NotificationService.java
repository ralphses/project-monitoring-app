package com.clicks.project_monitoring.service;

import com.clicks.project_monitoring.dtos.requests.notification.CreateNotificationRequest;
import com.clicks.project_monitoring.dtos.response.notification.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    public String create(CreateNotificationRequest request) {
        return null;
    }

    public List<NotificationDto> getAll(Integer page, String user) {
        return null;
    }
}
