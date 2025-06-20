package com.FinancialTransactionProcessor.dtos;

import com.FinancialTransactionProcessor.enums.NotificationChannel;
import com.FinancialTransactionProcessor.enums.NotificationStatus;
import com.FinancialTransactionProcessor.enums.NotificationType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class NotificationResponseDTO  {

    private String notificationId;
    private String userId;
    private NotificationType type;
    private String title;
    private String message;
    private NotificationStatus status;
    private NotificationChannel channel;
    private LocalDateTime sentAt;


}