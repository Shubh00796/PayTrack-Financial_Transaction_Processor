package com.FinancialTransactionProcessor.controllers;

import com.FinancialTransactionProcessor.dtos.TransactionResponseDTO;
import com.FinancialTransactionProcessor.enums.PaymentMethod;
import com.FinancialTransactionProcessor.enums.TransactionStatus;
import com.FinancialTransactionProcessor.enums.TransactionType;
import com.FinancialTransactionProcessor.service_interfaces.TransactionQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions/query")
@RequiredArgsConstructor
@Slf4j
public class TransactionQueryController {

    private final TransactionQueryService transactionQueryService;

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponseDTO> getTransactionById(@PathVariable String transactionId) {
        log.info("Fetching transaction by ID: {}", transactionId);
        return ResponseEntity.ok(transactionQueryService.getTransactionById(transactionId));
    }

    @GetMapping
    public ResponseEntity<Page<TransactionResponseDTO>> getAllTransactions(Pageable pageable) {
        log.info("Fetching all transactions with pagination");
        return ResponseEntity.ok(transactionQueryService.getAllTransactions(pageable));
    }

    @GetMapping("/status")
    public ResponseEntity<Page<TransactionResponseDTO>> getTransactionsByStatus(
            @RequestParam TransactionStatus status, Pageable pageable) {
        log.info("Fetching transactions by status: {}", status);
        return ResponseEntity.ok(transactionQueryService.getTransactionsByStatus(status, pageable));
    }

    @GetMapping("/account")
    public ResponseEntity<Page<TransactionResponseDTO>> getTransactionsByAccount(
            @RequestParam String accountId, Pageable pageable) {
        log.info("Fetching transactions for account: {}", accountId);
        return ResponseEntity.ok(transactionQueryService.getTransactionsByAccount(accountId, pageable));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsByDateRange(
            @RequestParam TransactionType type,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        log.info("Fetching transactions of type {} between {} and {}", type, startDate, endDate);
        return ResponseEntity.ok(transactionQueryService.getTransactionsByDateRange(type, startDate, endDate));
    }

    @GetMapping("/payment-method")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsByPaymentMethod(
            @RequestParam PaymentMethod paymentMethod) {
        log.info("Fetching transactions by payment method: {}", paymentMethod);
        return ResponseEntity.ok(transactionQueryService.getTransactionsByPaymentMethod(paymentMethod));
    }
}
