package com.FinancialTransactionProcessor.service_interfaces;


import com.FinancialTransactionProcessor.dtos.CreateFraudAlertDTO;
import com.FinancialTransactionProcessor.dtos.UpdateFraudAlertDTO;
import com.FinancialTransactionProcessor.dtos.FraudAlertResponseDTO;
import com.FinancialTransactionProcessor.enums.AlertStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FraudAlertService {

    FraudAlertResponseDTO createFraudAlert(CreateFraudAlertDTO createFraudAlertDTO);

    FraudAlertResponseDTO getFraudAlertById(String fraudAlertId);

    Page<FraudAlertResponseDTO> getAllFraudAlerts(Pageable pageable);

    Page<FraudAlertResponseDTO> getFraudAlertsByStatus(AlertStatus status, Pageable pageable);

    Page<FraudAlertResponseDTO> getFraudAlertsByTransactionId(String transactionId, Pageable pageable);

    FraudAlertResponseDTO updateFraudAlert(String fraudAlertId, UpdateFraudAlertDTO updateFraudAlertDTO);

    void deleteFraudAlert(String fraudAlertId);

    void updateFraudAlertStatus(String fraudAlertId, AlertStatus status);

    boolean isFraudAlertPending(String fraudAlertId);

    boolean isFraudAlertResolved(String fraudAlertId);
}
