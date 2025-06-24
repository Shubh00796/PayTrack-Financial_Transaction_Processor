package com.FinancialTransactionProcessor.reposiotry_interfaces;

import com.FinancialTransactionProcessor.entities.FraudAlert;
import com.FinancialTransactionProcessor.enums.AlertStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FraudAlertRepository extends JpaRepository<FraudAlert, Long> {

    Page<FraudAlert> findByStatus(AlertStatus status, Pageable pageable);

    Page<FraudAlert> findAll(Pageable pageable);

    @Query("SELECT f FROM FraudAlert f WHERE f.alertId = :alertId")
    Optional<FraudAlert> findByAlertId(@Param("alertId") String alertId);

    @Query("SELECT f FROM FraudAlert f WHERE f.ruleId = :ruleId")
    List<FraudAlert> findByRuleId(@Param("ruleId") String ruleId);

    @Query("SELECT f FROM FraudAlert f WHERE f.transactionId = :transactionId")
    Page<FraudAlert> findByTransactionId(@Param("transactionId") String transactionId, Pageable pageable);
}
