package com.FinancialTransactionProcessor.dtos;

import com.FinancialTransactionProcessor.enums.PaymentMethod;
import com.FinancialTransactionProcessor.enums.TransactionStatus;
import com.FinancialTransactionProcessor.enums.TransactionType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDTO  {

    private String transactionId;
    private String referenceId;
    private TransactionType transactionType;
    private String fromAccountId;
    private String toAccountId;
    private BigDecimal amount;
    private String currencyCode;
    private BigDecimal exchangeRate;
    private BigDecimal feeAmount;
    private String description;
    private TransactionStatus status;
    private PaymentMethod paymentMethod;
    private String initiatedBy;
    private LocalDateTime processedAt;
    private LocalDateTime completedAt;
    private String failureReason;
}