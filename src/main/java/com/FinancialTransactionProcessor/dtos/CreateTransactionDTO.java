package com.FinancialTransactionProcessor.dtos;

import com.FinancialTransactionProcessor.enums.PaymentMethod;
import com.FinancialTransactionProcessor.enums.TransactionType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionDTO {

    private String referenceId;
    private TransactionType transactionType;
    private String fromAccountId;
    private String toAccountId;
    private BigDecimal amount;
    private String currencyCode;
    private BigDecimal exchangeRate;
    private BigDecimal feeAmount;
    private String description;
    private PaymentMethod paymentMethod;
    private String initiatedBy;

    @PastOrPresent(message = "CreatedAt cannot be in the future")
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PastOrPresent(message = "UpdatedAt cannot be in the future")
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false, updatable = false)
    private LocalDateTime updatedAt;
}