package com.portage.app.service;

import com.portage.app.domain.enums.NotificationType;
import com.portage.app.dto.response.NotificationResponse;

import java.util.List;

public interface NotificationService {
    List<NotificationResponse> getForUser(Long userId);
    List<NotificationResponse> getUnreadForUser(Long userId);
    NotificationResponse markRead(Long notificationId);
    void markAllRead(Long userId);
    NotificationResponse create(Long userId, NotificationType type, String title, String message, String link);
    long countUnread(Long userId);
}
