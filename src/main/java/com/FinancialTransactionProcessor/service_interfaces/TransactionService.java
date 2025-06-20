package com.FinancialTransactionProcessor.service_interfaces;

import com.FinancialTransactionProcessor.dtos.CreateTransactionDTO;
import com.FinancialTransactionProcessor.dtos.TransactionResponseDTO;
import com.FinancialTransactionProcessor.enums.PaymentMethod;
import com.FinancialTransactionProcessor.enums.TransactionStatus;
import com.FinancialTransactionProcessor.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    TransactionResponseDTO initiateTransaction(CreateTransactionDTO createTransactionDTO);

    TransactionResponseDTO getTransactionById(String transactionId);

    Page<TransactionResponseDTO> getAllTransactions(Pageable pageable);

    Page<TransactionResponseDTO> getTransactionsByStatus(TransactionStatus status, Pageable pageable);

    Page<TransactionResponseDTO> getTransactionsByAccount(String accountId, Pageable pageable);

    TransactionResponseDTO cancelTransaction(String transactionId);

    TransactionResponseDTO reverseTransaction(String transactionId);

    void processTransaction(String transactionId);

    void updateTransactionStatus(String transactionId, TransactionStatus status);

    void updateTransactionType(String transactionId, TransactionType type);

    List<TransactionResponseDTO> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    List<TransactionResponseDTO> getTransactionsByPaymentMethod(PaymentMethod paymentMethod);

    boolean isTransactionPending(String transactionId);

    boolean isTransactionCompleted(String transactionId);

    boolean isTransactionFailed(String transactionId);

    void refundTransaction(String transactionId);

    void chargebackTransaction(String transactionId);


    void transferFunds(String fromAccountId, String toAccountId, BigDecimal amount);

    void depositFunds(String accountId, BigDecimal amount);

    void withdrawFunds(String accountId, BigDecimal amount);

}
