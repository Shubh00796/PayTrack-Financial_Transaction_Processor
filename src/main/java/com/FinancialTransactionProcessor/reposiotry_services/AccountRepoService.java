package com.FinancialTransactionProcessor.reposiotry_services;

import com.FinancialTransactionProcessor.entities.Account;
import com.FinancialTransactionProcessor.enums.AccountStatus;
import com.FinancialTransactionProcessor.enums.AccountType;
import com.FinancialTransactionProcessor.exceptions_handling.ResourceNotFoundException;
import com.FinancialTransactionProcessor.reposiotry_interfaces.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountRepoService {

    private final AccountRepository accountRepository;

    public Account findByAccountId(String accountId) {
        return accountRepository.findByAccountId(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account with ID " + accountId + " not found"));
    }

    @Transactional
    public Account findByAccountIdWithLock(String accountId) {
        return accountRepository.findByAccountIdWithLock(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account with ID " + accountId + " not found (locked)"));
    }

    public Page<Account> findByUserId(String userId, Pageable pageable) {
        return accountRepository.findByUserId(userId, pageable);
    }

    public Page<Account> findByStatus(AccountStatus status, Pageable pageable) {
        return accountRepository.findByStatus(status, pageable);
    }

    public Page<Account> findByUserIdAndStatus(String userId, AccountStatus status, Pageable pageable) {
        return accountRepository.findByUserIdAndStatus(userId, status, pageable);
    }

    public Page<Account> findByUserIdStatusAndCurrency(String userId, AccountStatus status, String currencyCode, Pageable pageable) {
        return accountRepository.findByUserIdAndStatusAndCurrency(userId, status, currencyCode, pageable);
    }

    @Transactional
    public int updateBalance(String accountId, BigDecimal amount) {
        return accountRepository.updateBalance(accountId, amount);
    }

    public boolean hasSufficientBalance(String accountId, BigDecimal amount) {
        return accountRepository.hasSufficientBalance(accountId, amount);
    }

    public List<Account> findByAccountType(AccountType accountType) {
        return accountRepository.findByAccountType(accountType.name());
    }

    public List<Account> findByCurrencyCode(String currencyCode) {
        return accountRepository.findByCurrencyCode(currencyCode);
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public Account update(Account account) {
        return accountRepository.save(account);
    }


    public void delete(Account account) {
        accountRepository.delete(account);
    }

    public boolean existsByAccountId(String accountId) {
        return accountRepository.findByAccountId(accountId).isPresent();
    }

    public boolean existsByUserId(String userId) {
        return accountRepository.findByUserId(userId).isPresent();
    }

    public Page<Account> getAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }
}
