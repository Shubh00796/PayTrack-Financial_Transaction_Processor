package com.FinancialTransactionProcessor.service_impls;

import com.FinancialTransactionProcessor.dtos.CreateFraudAlertDTO;
import com.FinancialTransactionProcessor.dtos.FraudAlertResponseDTO;
import com.FinancialTransactionProcessor.dtos.UpdateFraudAlertDTO;
import com.FinancialTransactionProcessor.entities.FraudAlert;
import com.FinancialTransactionProcessor.enums.AlertStatus;
import com.FinancialTransactionProcessor.exceptions_handling.ResourceNotFoundException;
import com.FinancialTransactionProcessor.mappers.FraudAlertMapper;
import com.FinancialTransactionProcessor.reposiotry_services.FraudAlertRepoService;
import com.FinancialTransactionProcessor.service_interfaces.FraudAlertService;
import com.FinancialTransactionProcessor.validation_utils.FraudAlertValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FraudAlertServiceImpl implements FraudAlertService {

    private final FraudAlertRepoService repoService;
    private final FraudAlertMapper mapper;
    private final FraudAlertValidator validator;

    @Override
    public FraudAlertResponseDTO createFraudAlert(CreateFraudAlertDTO dto) {
        validator.validateCreateRequest(dto);
        validateDuplicateAlert(dto);

        FraudAlert alert = mapper.toEntity(dto);
        alert.setAlertId(UUID.randomUUID().toString());
        alert.setStatus(AlertStatus.INVESTIGATING);

        FraudAlert saved = repoService.save(alert);
        return mapper.toDto(saved);
    }


    @Override
    public FraudAlertResponseDTO getFraudAlertById(String alertId) {
        return mapper.toDto(getAlertOrThrow(alertId));
    }

    @Override
    public Page<FraudAlertResponseDTO> getAllFraudAlerts(Pageable pageable) {
        return repoService.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public Page<FraudAlertResponseDTO> getFraudAlertsByStatus(AlertStatus status, Pageable pageable) {
        return repoService.findByStatus(status, pageable).map(mapper::toDto);
    }

    @Override
    public Page<FraudAlertResponseDTO> getFraudAlertsByTransactionId(String transactionId, Pageable pageable) {
        return repoService.findByTransactionId(transactionId, pageable).map(mapper::toDto);
    }

    @Override
    public FraudAlertResponseDTO updateFraudAlert(String alertId, UpdateFraudAlertDTO dto) {
        validator.validateUpdateRequest(dto);

        FraudAlert alert = getAlertOrThrow(alertId);
        mapper.updateEntity(alert, dto);
        alert.setUpdatedAt(LocalDateTime.now());

        FraudAlert updated = repoService.save(alert);
        log.info("Updated fraud alert with ID: {}", alertId);

        return mapper.toDto(updated);
    }

    @Override
    public void deleteFraudAlert(String alertId) {
        FraudAlert alert = getAlertOrThrow(alertId);
        repoService.delete(alert);
        log.info("Deleted fraud alert with ID: {}", alertId);
    }

    @Override
    public void updateFraudAlertStatus(String alertId, AlertStatus status) {
        FraudAlert alert = getAlertOrThrow(alertId);
        validateStatusTransition(alert, status);

        alert.setStatus(status);
        alert.setUpdatedAt(LocalDateTime.now());

        repoService.save(alert);
        log.info("Updated fraud alert status to {} for ID: {}", status, alertId);
    }

    @Override
    public boolean isFraudAlertPending(String alertId) {
        return isAlertStatus(alertId, AlertStatus.OPEN);
    }

    @Override
    public boolean isFraudAlertResolved(String alertId) {
        return isAlertStatus(alertId, AlertStatus.RESOLVED);
    }

    // 🔒 Private helper methods

    private FraudAlert getAlertOrThrow(String alertId) {
        FraudAlert alert = repoService.findByAlertId(alertId);
        if (alert == null) {
            throw new ResourceNotFoundException("Fraud alert not found for ID: " + alertId);
        }
        return alert;
    }

    private void validateDuplicateAlert(CreateFraudAlertDTO dto) {
        boolean isDuplicate = repoService.findByRuleId(dto.getRuleId()).stream()
                .anyMatch(alert -> alert.getTransactionId().equals(dto.getTransactionId()));
        if (isDuplicate) {
            throw new IllegalArgumentException("Duplicate fraud alert for transaction and rule");
        }
    }

    private void validateStatusTransition(FraudAlert alert, AlertStatus newStatus) {
        if (alert.getStatus() == AlertStatus.RESOLVED && newStatus != AlertStatus.RESOLVED) {
            throw new IllegalStateException("Cannot revert resolved alert to non-resolved status");
        }
    }

    private boolean isAlertStatus(String alertId, AlertStatus expectedStatus) {
        return expectedStatus.equals(getAlertOrThrow(alertId).getStatus());
    }
}
