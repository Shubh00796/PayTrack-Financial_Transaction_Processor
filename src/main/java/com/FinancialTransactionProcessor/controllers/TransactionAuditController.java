package com.FinancialTransactionProcessor.controllers;

import com.FinancialTransactionProcessor.dtos.CreateTransactionAuditDTO;
import com.FinancialTransactionProcessor.dtos.TransactionAuditResponseDTO;
import com.FinancialTransactionProcessor.dtos.UpdateTransactionAuditDTO;
import com.FinancialTransactionProcessor.enums.TransactionStatus;
import com.FinancialTransactionProcessor.service_interfaces.TransactionAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction-audits")
@RequiredArgsConstructor
public class TransactionAuditController {

    private final TransactionAuditService auditService;

    @PostMapping
    public ResponseEntity<TransactionAuditResponseDTO> createAudit(@RequestBody CreateTransactionAuditDTO dto) {
        return ResponseEntity.ok(auditService.createTransactionAudit(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionAuditResponseDTO> getAuditById(@PathVariable String id) {
        return ResponseEntity.ok(auditService.getTransactionAuditById(id));
    }

    @GetMapping
    public ResponseEntity<Page<TransactionAuditResponseDTO>> getAllAudits(Pageable pageable) {
        return ResponseEntity.ok(auditService.getAllTransactionAudits(pageable));
    }

    @GetMapping("/status")
    public ResponseEntity<Page<TransactionAuditResponseDTO>> getAuditsByStatus(
            @RequestParam TransactionStatus status, Pageable pageable) {
        return ResponseEntity.ok(auditService.getTransactionAuditsByStatus(status, pageable));
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<Page<TransactionAuditResponseDTO>> getAuditsByTransactionId(
            @PathVariable String transactionId, Pageable pageable) {
        return ResponseEntity.ok(auditService.getTransactionAuditsByTransactionId(transactionId, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionAuditResponseDTO> updateAudit(
            @PathVariable String id, @RequestBody UpdateTransactionAuditDTO dto) {
        return ResponseEntity.ok(auditService.updateTransactionAudit(id, dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateAuditStatus(
            @PathVariable String id, @RequestParam TransactionStatus status) {
        auditService.updateTransactionAuditStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAudit(@PathVariable String id) {
        auditService.deleteTransactionAudit(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/is-pending")
    public ResponseEntity<Boolean> isAuditPending(@PathVariable String id) {
        return ResponseEntity.ok(auditService.isTransactionAuditPending(id));
    }

    @GetMapping("/{id}/is-completed")
    public ResponseEntity<Boolean> isAuditCompleted(@PathVariable String id) {
        return ResponseEntity.ok(auditService.isTransactionAuditCompleted(id));
    }
}
