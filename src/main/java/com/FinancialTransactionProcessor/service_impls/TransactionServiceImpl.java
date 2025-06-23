package com.FinancialTransactionProcessor.service_impls;

import com.FinancialTransactionProcessor.dtos.CreateTransactionDTO;
import com.FinancialTransactionProcessor.dtos.TransactionResponseDTO;
import com.FinancialTransactionProcessor.entities.Transaction;
import com.FinancialTransactionProcessor.enums.TransactionStatus;
import com.FinancialTransactionProcessor.enums.TransactionType;
import com.FinancialTransactionProcessor.events.EventPublisher;
import com.FinancialTransactionProcessor.events.TransactionInitiatedEvent;
import com.FinancialTransactionProcessor.exceptions_handling.InsufficientBalanceException;
import com.FinancialTransactionProcessor.exceptions_handling.ResourceNotFoundException;
import com.FinancialTransactionProcessor.mappers.TransactionMapper;
import com.FinancialTransactionProcessor.reposiotry_services.TransactionRepoService;
import com.FinancialTransactionProcessor.service_interfaces.AccountService;
import com.FinancialTransactionProcessor.service_interfaces.TransactionService;
import com.FinancialTransactionProcessor.validation_utils.TransactionValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepoService repoService;
    private final TransactionMapper mapper;
    private final AccountService accountService;
    private final EventPublisher eventPublisher;
    private final TransactionValidator transactionValidator;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public TransactionResponseDTO initiateTransaction(CreateTransactionDTO createTransactionDTO) {
        transactionValidator.validateCreateRequest(createTransactionDTO);

        Transaction transaction = mapper.toEntity(createTransactionDTO);
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setStatus(TransactionStatus.PENDING);

        Transaction savedTransaction = repoService.save(transaction);
        TransactionResponseDTO responseDTO = mapper.toDto(savedTransaction);

        eventPublisher.publish(new TransactionInitiatedEvent(this, createTransactionDTO));

        return responseDTO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void processTransaction(String transactionId) {
        Transaction transaction = getTransactionById(transactionId);
        validateTransaction(transactionId, transaction);

        validateTransactionStatus(transaction, TransactionStatus.PENDING, "Only pending transactions can be processed.");

        transferFunds(transaction.getFromAccountId(), transaction.getToAccountId(), transaction.getAmount());

        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setProcessedAt(LocalDateTime.now());
        transaction.setCompletedAt(LocalDateTime.now());

        repoService.save(transaction);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void transferFunds(String fromAccountId, String toAccountId, BigDecimal amount) {
        validateUserHasSufficientBalance(fromAccountId, amount);

        accountService.debitBalance(fromAccountId, amount);
        accountService.creditBalance(toAccountId, amount);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void cancelTransaction(String transactionId) {
        Transaction transaction = getTransactionById(transactionId);
        validateTransaction(transactionId, transaction);

        validateTransactionStatus(transaction, TransactionStatus.PENDING, "Only pending transactions can be cancelled.");

        transaction.setStatus(TransactionStatus.CANCELLED);
        transaction.setCompletedAt(LocalDateTime.now());

        repoService.save(transaction);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void reverseTransaction(String transactionId) {
        Transaction transaction = getTransactionById(transactionId);
        validateTransaction(transactionId, transaction);

        validateTransactionStatus(transaction, TransactionStatus.COMPLETED, "Only completed transactions can be reversed.");

        transferFunds(transaction.getToAccountId(), transaction.getFromAccountId(), transaction.getAmount());

        transaction.setStatus(TransactionStatus.REVERSED);
        transaction.setCompletedAt(LocalDateTime.now());

        repoService.save(transaction);


    }

    @Override
    @Transactional
    public void updateTransactionStatus(String transactionId, TransactionStatus status) {
        Transaction transaction = getTransactionById(transactionId);
        validateTransaction(transactionId, transaction);

        transaction.setStatus(status);
        repoService.save(transaction);
    }

    private static void validateTransaction(String transactionId, Transaction transaction) {
        if (transaction == null) {
            throw new ResourceNotFoundException("Transaction not found: " + transactionId);
        }
    }

    @Override
    @Transactional
    public void updateTransactionType(String transactionId, TransactionType type) {
        Transaction transaction = getTransactionById(transactionId);
        validateTransaction(transactionId, transaction);

        transaction.setTransactionType(type);
        repoService.save(transaction);
    }

    @Override
    @Transactional
    public void refundTransaction(String transactionId) {
        Transaction transaction = getTransactionById(transactionId);
        validateTransaction(transactionId, transaction);

        validateTransactionStatus(transaction, TransactionStatus.COMPLETED, "Only completed transactions can be refunded.");

        transferFunds(transaction.getToAccountId(), transaction.getFromAccountId(), transaction.getAmount());

        transaction.setStatus(TransactionStatus.REFUNDED);
        transaction.setCompletedAt(LocalDateTime.now());

        repoService.save(transaction);
    }

    private Transaction getTransactionById(String transactionId) {
        Transaction transaction = repoService.findByTransactionId(transactionId);
        return transaction;
    }

    @Override
    @Transactional
    public void chargebackTransaction(String transactionId) {
        Transaction transaction = getTransactionById(transactionId);
        validateTransaction(transactionId, transaction);

        validateTransactionStatus(transaction, TransactionStatus.COMPLETED, "Only completed transactions can be chargebacked.");

        transferFunds(transaction.getToAccountId(), transaction.getFromAccountId(), transaction.getAmount());

        transaction.setStatus(TransactionStatus.CHARGEBACKED);
        transaction.setCompletedAt(LocalDateTime.now());

        repoService.save(transaction);
    }

    private static void validateTransactionStatus(Transaction transaction, TransactionStatus completed, String s) {
        if (transaction.getStatus() != completed) {
            throw new IllegalStateException(s);
        }
    }

    @Override
    @Transactional
    public void depositFunds(String accountId, BigDecimal amount) {
        accountService.creditBalance(accountId, amount);
    }

    @Override
    @Transactional
    public void withdrawFunds(String accountId, BigDecimal amount) {
        validateUserHasSufficientBalance(accountId, amount);

        accountService.debitBalance(accountId, amount);
    }

    private void validateUserHasSufficientBalance(String accountId, BigDecimal amount) {
        if (!accountService.hasSufficientBalance(accountId, amount)) {
            throw new InsufficientBalanceException("Insufficient balance in account: " + accountId);
        }
    }
}
