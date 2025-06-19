package com.FinancialTransactionProcessor.service_interfaces;


import com.FinancialTransactionProcessor.dtos.CreateNotificationDTO;
import com.FinancialTransactionProcessor.dtos.UpdateNotificationDTO;
import com.FinancialTransactionProcessor.dtos.NotificationResponseDTO;
import com.FinancialTransactionProcessor.enums.NotificationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

    NotificationResponseDTO createNotification(CreateNotificationDTO createNotificationDTO);

    NotificationResponseDTO getNotificationById(String notificationId);

    Page<NotificationResponseDTO> getAllNotifications(Pageable pageable);

    Page<NotificationResponseDTO> getNotificationsByStatus(NotificationStatus status, Pageable pageable);

    Page<NotificationResponseDTO> getNotificationsByUserId(String userId, Pageable pageable);

    NotificationResponseDTO updateNotification(String notificationId, UpdateNotificationDTO updateNotificationDTO);

    void deleteNotification(String notificationId);

    void updateNotificationStatus(String notificationId, NotificationStatus status);

    boolean isNotificationPending(String notificationId);

    boolean isNotificationSent(String notificationId);
}
