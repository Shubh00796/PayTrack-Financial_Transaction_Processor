package com.FinancialTransactionProcessor.service_interfaces;

import com.FinancialTransactionProcessor.dtos.TransactionResponseDTO;
import com.FinancialTransactionProcessor.enums.PaymentMethod;
import com.FinancialTransactionProcessor.enums.TransactionStatus;
import com.FinancialTransactionProcessor.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionQueryService {

    TransactionResponseDTO getTransactionById(String transactionId);

    Page<TransactionResponseDTO> getAllTransactions(Pageable pageable);

    Page<TransactionResponseDTO> getTransactionsByStatus(TransactionStatus status, Pageable pageable);

    Page<TransactionResponseDTO> getTransactionsByAccount(String accountId, Pageable pageable);

    List<TransactionResponseDTO> getTransactionsByDateRange(TransactionType type, LocalDateTime startDate, LocalDateTime endDate);

    List<TransactionResponseDTO> getTransactionsByPaymentMethod(PaymentMethod paymentMethod);
}