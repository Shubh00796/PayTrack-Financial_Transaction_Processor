package com.FinancialTransactionProcessor.dtos;

import com.FinancialTransactionProcessor.enums.AlertType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

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

    @PastOrPresent(message = "CreatedAt cannot be in the future")
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PastOrPresent(message = "UpdatedAt cannot be in the future")
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false, updatable = false)
    private LocalDateTime updatedAt;
}
