package com.FinancialTransactionProcessor.controllers;

import com.FinancialTransactionProcessor.dtos.CreateTransactionDTO;
import com.FinancialTransactionProcessor.dtos.TransactionResponseDTO;
import com.FinancialTransactionProcessor.service_interfaces.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> initiateTransaction(
            @Valid @RequestBody CreateTransactionDTO createTransactionDTO) {
        log.info("Initiating transaction for: {}", createTransactionDTO);
        TransactionResponseDTO response = transactionService.initiateTransaction(createTransactionDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{transactionId}/process")
    public ResponseEntity<String> processTransaction(@PathVariable String transactionId) {
        transactionService.processTransaction(transactionId);
        return ResponseEntity.ok("Transaction processed successfully.");
    }

    @PutMapping("/{transactionId}/cancel")
    public ResponseEntity<String> cancelTransaction(@PathVariable String transactionId) {
        transactionService.cancelTransaction(transactionId);
        return ResponseEntity.ok("Transaction cancelled successfully.");
    }

    @PutMapping("/{transactionId}/reverse")
    public ResponseEntity<String> reverseTransaction(@PathVariable String transactionId) {
        transactionService.reverseTransaction(transactionId);
        return ResponseEntity.ok("Transaction reversed successfully.");
    }

    @PutMapping("/{transactionId}/refund")
    public ResponseEntity<String> refundTransaction(@PathVariable String transactionId) {
        transactionService.refundTransaction(transactionId);
        return ResponseEntity.ok("Transaction refunded successfully.");
    }

    @PutMapping("/{transactionId}/chargeback")
    public ResponseEntity<String> chargebackTransaction(@PathVariable String transactionId) {
        transactionService.chargebackTransaction(transactionId);
        return ResponseEntity.ok("Transaction charged back successfully.");
    }

    @PutMapping("/deposit/{accountId}")
    public ResponseEntity<String> depositFunds(@PathVariable String accountId, @RequestBody BigDecimal amount) {
        transactionService.depositFunds(accountId, amount);
        return ResponseEntity.ok("Funds deposited successfully.");
    }

    @PutMapping("/withdraw/{accountId}")
    public ResponseEntity<String> withdrawFunds(@PathVariable String accountId, @RequestBody BigDecimal amount) {
        transactionService.withdrawFunds(accountId, amount);
        return ResponseEntity.ok("Funds withdrawn successfully.");
    }
}
