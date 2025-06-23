package com.FinancialTransactionProcessor.service_impls;

import com.FinancialTransactionProcessor.dtos.AccountResponseDTO;
import com.FinancialTransactionProcessor.dtos.CreateAccountDTO;
import com.FinancialTransactionProcessor.dtos.UpdateAccountDTO;
import com.FinancialTransactionProcessor.entities.Account;
import com.FinancialTransactionProcessor.enums.AccountStatus;
import com.FinancialTransactionProcessor.enums.AccountType;
import com.FinancialTransactionProcessor.exceptions_handling.ResourceNotFoundException;
import com.FinancialTransactionProcessor.mappers.AccountMapper;
import com.FinancialTransactionProcessor.reposiotry_services.AccountRepoService;
import com.FinancialTransactionProcessor.service_interfaces.AccountService;
import com.FinancialTransactionProcessor.service_interfaces.UserService;
import com.FinancialTransactionProcessor.validation_utils.AccountValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for account-related operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepoService repoService;
    private final AccountMapper mapper;
    private final UserService userService;
    private final AccountValidator validator;

    /**
     * Creates a new account.
     *
     * @param requestDto the create account request DTO
     * @return the created account response DTO
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public AccountResponseDTO createAccount(CreateAccountDTO requestDto) {
        validator.validateCreateRequest(requestDto);
        validateUserHasNoExistingAccount(requestDto.getUserId());
        Account account = mapper.toEntity(requestDto);
        account.setAccountId(requestDto.getAccountId());
        return mapper.toDto(repoService.save(account));
    }


    /**
     * Retrieves an account by ID.
     *
     * @param accountId the account ID
     * @return the account response DTO
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public AccountResponseDTO getAccountById(String accountId) {
        return mapper.toDto(getAccountOrThrow(accountId));
    }

    /**
     * Retrieves all accounts.
     *
     * @param pageable the pagination information
     * @return the page of account response DTOs
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public Page<AccountResponseDTO> getAllAccounts(Pageable pageable) {
        return repoService.getAllAccounts(pageable).map(mapper::toDto);
    }

    /**
     * Retrieves accounts by status.
     *
     * @param status   the account status
     * @param pageable the pagination information
     * @return the page of account response DTOs
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public Page<AccountResponseDTO> getAccountsByStatus(AccountStatus status, Pageable pageable) {
        return repoService.findByStatus(status, pageable).map(mapper::toDto);
    }

    /**
     * Retrieves accounts by user ID.
     *
     * @param userId   the user ID
     * @param pageable the pagination information
     * @return the page of account response DTOs
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public Page<AccountResponseDTO> getAccountsByUser(String userId, Pageable pageable) {
        return repoService.findByUserId(userId, pageable).map(mapper::toDto);
    }

    /**
     * Updates an account.
     *
     * @param accountId  the account ID
     * @param requestDto the update account request DTO
     * @return the updated account response DTO
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public AccountResponseDTO updateAccount(String accountId, UpdateAccountDTO requestDto) {
        Account account = getAccountOrThrow(accountId);
        mapper.updateEntity(account, requestDto);
        return mapper.toDto(account);
    }

    /**
     * Closes an account.
     *
     * @param accountId the account ID
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void closeAccount(String accountId) {
        updateAccountStatus(accountId, AccountStatus.CLOSED);
    }

    /**
     * Freezes an account.
     *
     * @param accountId the account ID
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void freezeAccount(String accountId) {
        updateAccountStatus(accountId, AccountStatus.FROZEN);
    }

    /**
     * Unfreezes an account.
     *
     * @param accountId the account ID
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void unfreezeAccount(String accountId) {
        updateAccountStatus(accountId, AccountStatus.ACTIVE);
    }

    /**
     * Checks if an account has sufficient balance.
     *
     * @param accountId the account ID
     * @param amount    the amount to check
     * @return true if the account has sufficient balance, false otherwise
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public boolean hasSufficientBalance(String accountId, BigDecimal amount) {
        if (validator.isInvalidAmount(amount)) return false;
        Account account = getAccountOrThrow(accountId);
        return account.getStatus() == AccountStatus.ACTIVE &&
                account.getAvailableBalance().compareTo(amount) >= 0;
    }

    /**
     * Updates the balance of an account.
     *
     * @param accountId the account ID
     * @param amount    the new balance
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void updateBalance(String accountId, BigDecimal amount) {
        validator.validateAmount(amount);
        Account account = getAccountOrThrow(accountId);
        validator.validateAccountStatus(account);
        account.setBalance(amount);
        account.setAvailableBalance(amount);
    }

    /**
     * Updates the daily limit of an account.
     *
     * @param accountId  the account ID
     * @param dailyLimit the new daily limit
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void updateDailyLimit(String accountId, BigDecimal dailyLimit) {
        updateLimit(accountId, dailyLimit);
    }

    /**
     * Updates the monthly limit of an account.
     *
     * @param accountId    the account ID
     * @param monthlyLimit the new monthly limit
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void updateMonthlyLimit(String accountId, BigDecimal monthlyLimit) {
        updateLimit(accountId, monthlyLimit);
    }

    /**
     * Retrieves accounts by currency code.
     *
     * @param currencyCode the currency code
     * @return the list of account response DTOs
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public List<AccountResponseDTO> getAccountsByCurrency(String currencyCode) {
        return mapToDtoList(repoService.findByCurrencyCode(currencyCode));
    }

    /**
     * Retrieves accounts by account type.
     *
     * @param accountType the account type
     * @return the list of account response DTOs
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public List<AccountResponseDTO> getAccountsByAccountType(AccountType accountType) {
        return mapToDtoList(repoService.findByAccountType(accountType));
    }

    /**
     * Updates the account status.
     *
     * @param accountId the account ID
     * @param status    the new account status
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void updateAccountStatus(String accountId, AccountStatus status) {
        Account account = getAccountOrThrow(accountId);
        account.setStatus(status);
    }

    /**
     * Updates the account type.
     *
     * @param accountId   the account ID
     * @param accountType the new account type
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void updateAccountType(String accountId, AccountType accountType) {
        Objects.requireNonNull(accountType, "Account type cannot be null");
        Account account = getAccountOrThrow(accountId);
        account.setAccountType(accountType);
    }

    /**
     * Checks if an account is active.
     *
     * @param accountId the account ID
     * @return true if the account is active, false otherwise
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public boolean isAccountActive(String accountId) {
        return getAccountOrThrow(accountId).getStatus() == AccountStatus.ACTIVE;
    }

    /**
     * Checks if an account is frozen.
     *
     * @param accountId the account ID
     * @return true if the account is frozen, false otherwise
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public boolean isAccountFrozen(String accountId) {
        return getAccountOrThrow(accountId).getStatus() == AccountStatus.FROZEN;
    }

    @Override
    @Transactional
    public void debitBalance(String accountId, BigDecimal amount) {
        validator.validateAmount(amount);
        Account account = repoService.findByAccountIdWithLock(accountId);
        validator.validateAccountStatus(account);

        if (account.getAvailableBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient balance in account: " + accountId);
        }

        account.setBalance(account.getBalance().subtract(amount));
        account.setAvailableBalance(account.getAvailableBalance().subtract(amount));
        repoService.save(account);
    }

    @Override
    @Transactional
    public void creditBalance(String accountId, BigDecimal amount) {
        validator.validateAmount(amount);
        Account account = repoService.findByAccountIdWithLock(accountId);
        validator.validateAccountStatus(account);

        account.setBalance(account.getBalance().add(amount));
        account.setAvailableBalance(account.getAvailableBalance().add(amount));
        repoService.save(account);
    }


    // Private helpers
    private Account getAccountOrThrow(String accountId) {
        return Optional.ofNullable(repoService.findByAccountId(accountId))
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + accountId));
    }

    private void updateLimit(String accountId, BigDecimal limit) {
        validator.validateLimit(limit);
        Account account = getAccountOrThrow(accountId);
        validator.validateAccountStatus(account);
        account.setMonthlyLimit(limit);
    }

    private <T> List<AccountResponseDTO> mapToDtoList(List<Account> accounts) {
        return accounts.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    private void validateUserHasNoExistingAccount(String userId) {
        if (repoService.existsByUserId(userId)) {
            throw new IllegalArgumentException("User already has an account: " + userId);
        }
    }
}