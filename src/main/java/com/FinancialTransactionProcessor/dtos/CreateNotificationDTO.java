package com.FinancialTransactionProcessor.dtos;

import com.FinancialTransactionProcessor.enums.NotificationChannel;
import com.FinancialTransactionProcessor.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotificationDTO {

    private String userId;
    private NotificationType type;
    private String title;
    private String message;
    private NotificationChannel channel;
    private String metadata;
}