package com.portage.app.service.impl;

import com.portage.app.domain.entity.Notification;
import com.portage.app.domain.entity.User;
import com.portage.app.domain.enums.NotificationType;
import com.portage.app.dto.response.NotificationResponse;
import com.portage.app.exception.ResourceNotFoundException;
import com.portage.app.mapper.NotificationMapper;
import com.portage.app.repository.NotificationRepository;
import com.portage.app.repository.UserRepository;
import com.portage.app.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getForUser(Long userId) {
        return mapper.toResponseList(notificationRepository.findByUserIdOrderByCreatedAtDesc(userId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getUnreadForUser(Long userId) {
        return mapper.toResponseList(notificationRepository.findByUserIdAndReadFalse(userId));
    }

    @Override
    public NotificationResponse markRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", notificationId));
        notification.setRead(true);
        return mapper.toResponse(notificationRepository.save(notification));
    }

    @Override
    public void markAllRead(Long userId) {
        List<Notification> unread = notificationRepository.findByUserIdAndReadFalse(userId);
        unread.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(unread);
    }

    @Override
    public NotificationResponse create(Long userId, NotificationType type, String title, String message, String link) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        Notification notification = Notification.builder()
                .user(user)
                .type(type)
                .title(title)
                .message(message)
                .link(link)
                .read(false)
                .build();

        return mapper.toResponse(notificationRepository.save(notification));
    }

    @Override
    @Transactional(readOnly = true)
    public long countUnread(Long userId) {
        return notificationRepository.countByUserIdAndReadFalse(userId);
    }
}
