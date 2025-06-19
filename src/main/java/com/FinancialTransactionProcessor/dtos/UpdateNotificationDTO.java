package com.FinancialTransactionProcessor.dtos;

import com.FinancialTransactionProcessor.enums.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UpdateNotificationDTO {

    private NotificationStatus status;
    private LocalDateTime sentAt;
}
