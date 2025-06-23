package com.FinancialTransactionProcessor.service_interfaces;

import com.FinancialTransactionProcessor.dtos.AccountResponseDTO;
import com.FinancialTransactionProcessor.dtos.CreateAccountDTO;
import com.FinancialTransactionProcessor.dtos.UpdateAccountDTO;
import com.FinancialTransactionProcessor.enums.AccountStatus;
import com.FinancialTransactionProcessor.enums.AccountType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    AccountResponseDTO createAccount(CreateAccountDTO requestDto);

    AccountResponseDTO getAccountById(String accountId);

    Page<AccountResponseDTO> getAllAccounts(Pageable pageable);

    Page<AccountResponseDTO> getAccountsByStatus(AccountStatus status, Pageable pageable);

    Page<AccountResponseDTO> getAccountsByUser(String userId, Pageable pageable);

    AccountResponseDTO updateAccount(String accountId, UpdateAccountDTO requestDto);

    void closeAccount(String accountId);

    void freezeAccount(String accountId);

    void unfreezeAccount(String accountId);

    boolean hasSufficientBalance(String accountId, BigDecimal amount);

    void updateBalance(String accountId, BigDecimal amount);

    void updateDailyLimit(String accountId, BigDecimal dailyLimit);

    void updateMonthlyLimit(String accountId, BigDecimal monthlyLimit);

    List<AccountResponseDTO> getAccountsByCurrency(String currencyCode);

    List<AccountResponseDTO> getAccountsByAccountType(AccountType accountType);


    void updateAccountStatus(String accountId, AccountStatus status);


    boolean isAccountActive(String accountId);

    boolean isAccountFrozen(String accountId);


    void debitBalance(String accountId, BigDecimal amount);

    void creditBalance(String accountId, BigDecimal amount);

}