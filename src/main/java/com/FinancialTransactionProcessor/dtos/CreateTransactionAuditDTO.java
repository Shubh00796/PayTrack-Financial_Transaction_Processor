package com.FinancialTransactionProcessor.dtos;

import com.FinancialTransactionProcessor.enums.TransactionStatus;
import jakarta.persistence.Column;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CreateTransactionAuditDTO {

    private String transactionId;
    private TransactionStatus previousStatus;
    private TransactionStatus newStatus;
    private String changedBy;
    private String changeReason;
    private String metadata;


}