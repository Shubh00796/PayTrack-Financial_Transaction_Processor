package com.FinancialTransactionProcessor.dtos;

import com.FinancialTransactionProcessor.enums.NotificationChannel;
import com.FinancialTransactionProcessor.enums.NotificationStatus;
import com.FinancialTransactionProcessor.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NotificationResponseDTO {

    private String notificationId;
    private String userId;
    private NotificationType type;
    private String title;
    private String message;
    private NotificationStatus status;
    private NotificationChannel channel;
    private LocalDateTime sentAt;
}