package com.FinancialTransactionProcessor.dtos;

import com.FinancialTransactionProcessor.enums.AlertStatus;
import com.FinancialTransactionProcessor.enums.AlertType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FraudAlertResponseDTO extends BaseDTO {

    private String alertId;
    private String transactionId;
    private String ruleId;
    private Integer riskScore;
    private AlertType alertType;
    private AlertStatus status;
    private String details;
    private String resolvedBy;
    private LocalDateTime resolvedAt;
}
