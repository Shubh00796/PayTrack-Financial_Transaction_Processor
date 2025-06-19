package com.FinancialTransactionProcessor.dtos;

import com.FinancialTransactionProcessor.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TransactionAuditResponseDTO extends BaseDTO {

    private String transactionId;
    private TransactionStatus previousStatus;
    private TransactionStatus newStatus;
    private String changedBy;
    private String changeReason;
    private String metadata;
}
