package com.FinancialTransactionProcessor.dtos;

import com.FinancialTransactionProcessor.enums.AlertType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateFraudAlertDTO {

    private String transactionId;
    private String ruleId;
    private Integer riskScore;
    private AlertType alertType;
    private String details;
}
