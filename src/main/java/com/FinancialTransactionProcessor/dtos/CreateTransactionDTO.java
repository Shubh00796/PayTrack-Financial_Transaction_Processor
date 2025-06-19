package com.FinancialTransactionProcessor.dtos;

import com.FinancialTransactionProcessor.enums.PaymentMethod;
import com.FinancialTransactionProcessor.enums.TransactionType;
import lombok.*;

import java.math.BigDecimal;

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
}