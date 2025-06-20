package com.FinancialTransactionProcessor.dtos;

import com.FinancialTransactionProcessor.enums.AlertStatus;
import com.FinancialTransactionProcessor.enums.AlertType;
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

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FraudAlertResponseDTO  {

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
