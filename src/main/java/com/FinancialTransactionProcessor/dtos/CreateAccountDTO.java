package com.FinancialTransactionProcessor.dtos;

import com.FinancialTransactionProcessor.enums.AccountStatus;
import com.FinancialTransactionProcessor.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountDTO {

    private String userId;
    private AccountType accountType;
    private String currencyCode;
    private BigDecimal balance;
    private BigDecimal availableBalance;
    private BigDecimal frozenAmount;
    private BigDecimal dailyLimit;
    private BigDecimal monthlyLimit;
    private AccountStatus status;
}
