package com.FinancialTransactionProcessor.dtos;

import com.FinancialTransactionProcessor.enums.AlertStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFraudAlertDTO {

    private AlertStatus status;
    private String resolvedBy;
    private LocalDateTime resolvedAt;
}