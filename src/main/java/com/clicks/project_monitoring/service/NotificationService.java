package com.clicks.project_monitoring.service;

import com.clicks.project_monitoring.dtos.requests.notification.CreateNotificationRequest;
import com.clicks.project_monitoring.dtos.response.notification.NotificationDto;
import com.clicks.project_monitoring.exceptions.ResourceNotFoundException;
import com.clicks.project_monitoring.model.Notification;
import com.clicks.project_monitoring.repositories.NotificationRepository;
import com.clicks.project_monitoring.utils.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;

    public String create(CreateNotificationRequest request) {
        if (notificationRepository.existsByUserReference(request.sender(), request.recipient())) {
            Notification newNotification = Notification.builder()
                    .sender(request.sender())
                    .receiver(request.recipient())
                    .message(request.message())
                    .reference(UUID.randomUUID().toString())
                    .build();
            notificationRepository.save(newNotification);
            return "Notification sent successfully";
        }
        throw new ResourceNotFoundException("Sender or Receiver not valid");
    }

    public List<NotificationDto> getAll(Integer page, String user) {
        Page<Notification> notifications = notificationRepository
                .findAllByReceiver(user, PageRequest.of(Math.max(0, (page - 1)), 10));

        return notifications.get().map(notification ->
                new NotificationDto(
                        notification.getMessage(),
                        userService.findUserName(notification.getSender()),
                        userService.findUserName(notification.getReceiver()),
                        notification.getTimestamp().format(DtoMapper.DATE_FORMATTER)))
                .toList();
    }

}
