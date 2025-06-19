package com.FinancialTransactionProcessor.dtos;

import com.FinancialTransactionProcessor.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UpdateTransactionAuditDTO {

    private TransactionStatus previousStatus;
    private TransactionStatus newStatus;
    private String changedBy;
    private String changeReason;
    private String metadata;
}