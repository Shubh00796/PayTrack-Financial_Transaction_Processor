package com.FinancialTransactionProcessor.controllers;

import com.FinancialTransactionProcessor.dtos.CreateFraudAlertDTO;
import com.FinancialTransactionProcessor.dtos.FraudAlertResponseDTO;
import com.FinancialTransactionProcessor.dtos.UpdateFraudAlertDTO;
import com.FinancialTransactionProcessor.enums.AlertStatus;
import com.FinancialTransactionProcessor.service_interfaces.FraudAlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fraud-alerts")
@RequiredArgsConstructor
public class FraudAlertController {

    private final FraudAlertService fraudAlertService;

    @PostMapping
    public ResponseEntity<FraudAlertResponseDTO> createFraudAlert(@RequestBody CreateFraudAlertDTO dto) {
        return ResponseEntity.ok(fraudAlertService.createFraudAlert(dto));
    }

    @GetMapping("/{alertId}")
    public ResponseEntity<FraudAlertResponseDTO> getFraudAlertById(@PathVariable String alertId) {
        return ResponseEntity.ok(fraudAlertService.getFraudAlertById(alertId));
    }

    @GetMapping
    public ResponseEntity<Page<FraudAlertResponseDTO>> getAllFraudAlerts(Pageable pageable) {
        return ResponseEntity.ok(fraudAlertService.getAllFraudAlerts(pageable));
    }

    @GetMapping("/status")
    public ResponseEntity<Page<FraudAlertResponseDTO>> getFraudAlertsByStatus(
            @RequestParam AlertStatus status, Pageable pageable) {
        return ResponseEntity.ok(fraudAlertService.getFraudAlertsByStatus(status, pageable));
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<Page<FraudAlertResponseDTO>> getFraudAlertsByTransactionId(
            @PathVariable String transactionId, Pageable pageable) {
        return ResponseEntity.ok(fraudAlertService.getFraudAlertsByTransactionId(transactionId, pageable));
    }

    @PutMapping("/{alertId}")
    public ResponseEntity<FraudAlertResponseDTO> updateFraudAlert(
            @PathVariable String alertId, @RequestBody UpdateFraudAlertDTO dto) {
        return ResponseEntity.ok(fraudAlertService.updateFraudAlert(alertId, dto));
    }

    @PatchMapping("/{alertId}/status")
    public ResponseEntity<Void> updateFraudAlertStatus(
            @PathVariable String alertId, @RequestParam AlertStatus status) {
        fraudAlertService.updateFraudAlertStatus(alertId, status);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{alertId}")
    public ResponseEntity<Void> deleteFraudAlert(@PathVariable String alertId) {
        fraudAlertService.deleteFraudAlert(alertId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{alertId}/is-pending")
    public ResponseEntity<Boolean> isFraudAlertPending(@PathVariable String alertId) {
        return ResponseEntity.ok(fraudAlertService.isFraudAlertPending(alertId));
    }

    @GetMapping("/{alertId}/is-resolved")
    public ResponseEntity<Boolean> isFraudAlertResolved(@PathVariable String alertId) {
        return ResponseEntity.ok(fraudAlertService.isFraudAlertResolved(alertId));
    }
}
