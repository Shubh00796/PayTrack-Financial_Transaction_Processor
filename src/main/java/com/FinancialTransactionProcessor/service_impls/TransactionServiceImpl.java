package com.FinancialTransactionProcessor.service_impls;

import com.FinancialTransactionProcessor.dtos.CreateTransactionDTO;
import com.FinancialTransactionProcessor.dtos.TransactionResponseDTO;
import com.FinancialTransactionProcessor.entities.Transaction;
import com.FinancialTransactionProcessor.enums.TransactionStatus;
import com.FinancialTransactionProcessor.enums.TransactionType;
import com.FinancialTransactionProcessor.events.TransactionInitiatedEvent;
import com.FinancialTransactionProcessor.exceptions_handling.InsufficientBalanceException;
import com.FinancialTransactionProcessor.exceptions_handling.ResourceNotFoundException;
import com.FinancialTransactionProcessor.mappers.TransactionMapper;
import com.FinancialTransactionProcessor.reposiotry_services.TransactionRepoService;
import com.FinancialTransactionProcessor.service_interfaces.AccountService;
import com.FinancialTransactionProcessor.service_interfaces.TransactionService;
import com.FinancialTransactionProcessor.validation_utils.TransactionValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepoService repoService;
    private final TransactionMapper mapper;
    private final AccountService accountService;
    private final ApplicationEventPublisher eventPublisher;
    private final TransactionValidator validator;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public TransactionResponseDTO initiateTransaction(CreateTransactionDTO dto) {
        validator.validateCreateRequest(dto);

        Transaction transaction = mapper.toEntity(dto);
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setStatus(TransactionStatus.PENDING);

        Transaction saved = repoService.save(transaction);
        eventPublisher.publishEvent(new TransactionInitiatedEvent(this, dto));

        return mapper.toDto(saved);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void processTransaction(String transactionId) {
        Transaction transaction = getValidatedTransaction(transactionId, TransactionStatus.PENDING,
                "Only pending transactions can be processed.");

        transferFunds(transaction.getFromAccountId(), transaction.getToAccountId(), transaction.getAmount());

        updateTransactionStatusAndTimestamp(transaction, TransactionStatus.COMPLETED);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void transferFunds(String fromAccountId, String toAccountId, BigDecimal amount) {
        validateSufficientBalance(fromAccountId, amount);
        accountService.debitBalance(fromAccountId, amount);
        accountService.creditBalance(toAccountId, amount);
    }

    @Override
    @Transactional
    public void cancelTransaction(String transactionId) {
        Transaction transaction = getValidatedTransaction(transactionId, TransactionStatus.PENDING,
                "Only pending transactions can be cancelled.");

        updateTransactionStatusAndTimestamp(transaction, TransactionStatus.CANCELLED);
    }

    @Override
    @Transactional
    public void reverseTransaction(String transactionId) {
        Transaction transaction = getValidatedTransaction(transactionId, TransactionStatus.COMPLETED,
                "Only completed transactions can be reversed.");

        transferFunds(transaction.getToAccountId(), transaction.getFromAccountId(), transaction.getAmount());
        updateTransactionStatusAndTimestamp(transaction, TransactionStatus.REVERSED);
    }

    @Override
    @Transactional
    public void refundTransaction(String transactionId) {
        Transaction transaction = getValidatedTransaction(transactionId, TransactionStatus.COMPLETED,
                "Only completed transactions can be refunded.");

        transferFunds(transaction.getToAccountId(), transaction.getFromAccountId(), transaction.getAmount());
        updateTransactionStatusAndTimestamp(transaction, TransactionStatus.REFUNDED);
    }

    @Override
    @Transactional
    public void chargebackTransaction(String transactionId) {
        Transaction transaction = getValidatedTransaction(transactionId, TransactionStatus.COMPLETED,
                "Only completed transactions can be chargebacked.");

        transferFunds(transaction.getToAccountId(), transaction.getFromAccountId(), transaction.getAmount());
        updateTransactionStatusAndTimestamp(transaction, TransactionStatus.CHARGEBACKED);
    }

    @Override
    @Transactional
    public void updateTransactionStatus(String transactionId, TransactionStatus status) {
        Transaction transaction = getTransactionOrThrow(transactionId);
        transaction.setStatus(status);
        repoService.save(transaction);
    }

    @Override
    @Transactional
    public void updateTransactionType(String transactionId, TransactionType type) {
        Transaction transaction = getTransactionOrThrow(transactionId);
        transaction.setTransactionType(type);
        repoService.save(transaction);
    }

    @Override
    @Transactional
    public void depositFunds(String accountId, BigDecimal amount) {
        accountService.creditBalance(accountId, amount);
    }

    @Override
    @Transactional
    public void withdrawFunds(String accountId, BigDecimal amount) {
        validateSufficientBalance(accountId, amount);
        accountService.debitBalance(accountId, amount);
    }

    // 🔒 Private helper methods
    private Transaction getTransactionOrThrow(String transactionId) {
        Transaction transaction = repoService.findByTransactionId(transactionId);
        if (transaction == null) {
            throw new ResourceNotFoundException("Transaction not found: " + transactionId);
        }
        return transaction;
    }

    private Transaction getValidatedTransaction(String transactionId, TransactionStatus expectedStatus, String errorMessage) {
        Transaction transaction = getTransactionOrThrow(transactionId);
        if (transaction.getStatus() != expectedStatus) {
            throw new IllegalStateException(errorMessage);
        }
        return transaction;
    }

    private void updateTransactionStatusAndTimestamp(Transaction transaction, TransactionStatus status) {
        transaction.setStatus(status);
        transaction.setCompletedAt(LocalDateTime.now());
        if (status == TransactionStatus.COMPLETED) {
            transaction.setProcessedAt(LocalDateTime.now());
        }
        repoService.save(transaction);
    }

    private void validateSufficientBalance(String accountId, BigDecimal amount) {
        if (!accountService.hasSufficientBalance(accountId, amount)) {
            throw new InsufficientBalanceException("Insufficient balance in account: " + accountId);
        }
    }
}
