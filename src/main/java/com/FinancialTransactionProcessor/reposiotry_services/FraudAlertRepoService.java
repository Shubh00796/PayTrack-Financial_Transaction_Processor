package com.FinancialTransactionProcessor.reposiotry_services;

import com.FinancialTransactionProcessor.entities.FraudAlert;
import com.FinancialTransactionProcessor.enums.AlertStatus;
import com.FinancialTransactionProcessor.exceptions_handling.ResourceNotFoundException;
import com.FinancialTransactionProcessor.reposiotry_interfaces.FraudAlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FraudAlertRepoService {

    private final FraudAlertRepository fraudAlertRepository;

    public FraudAlert findById(Long id) {
        return fraudAlertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fraud alert with ID " + id + " not found"));
    }

    public FraudAlert findByAlertId(String alertId) {
        return fraudAlertRepository.findByAlertId(alertId)
                .orElseThrow(() -> new ResourceNotFoundException("Fraud alert with alert ID " + alertId + " not found"));
    }

    public Page<FraudAlert> findByStatus(AlertStatus status, Pageable pageable) {
        return fraudAlertRepository.findByStatus(status, pageable);
    }

    public Page<FraudAlert> findByTransactionId(String transactionId, Pageable pageable) {
        return fraudAlertRepository.findByTransactionId(transactionId, pageable);
    }

    public List<FraudAlert> findByRuleId(String ruleId) {
        return fraudAlertRepository.findByRuleId(ruleId);
    }

    public Page<FraudAlert> findAll(Pageable pageable) {
        return fraudAlertRepository.findAll(pageable);
    }

    public FraudAlert save(FraudAlert fraudAlert) {
        return fraudAlertRepository.save(fraudAlert);
    }

    public void delete(FraudAlert fraudAlert) {
        fraudAlertRepository.delete(fraudAlert);
    }

    public boolean existsByAlertId(String alertId) {
        return fraudAlertRepository.findByAlertId(alertId).isPresent();
    }
}
