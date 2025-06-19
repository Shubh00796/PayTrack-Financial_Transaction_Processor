package com.FinancialTransactionProcessor.entities;

import com.FinancialTransactionProcessor.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "transaction_audit", indexes = {
        @Index(name = "idx_transaction_id", columnList = "transaction_id"),
        @Index(name = "idx_created_at", columnList = "created_at")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class TransactionAudit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_id", nullable = false, length = 50)
    private String transactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "previous_status", nullable = false, length = 20)
    private TransactionStatus previousStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false, length = 20)
    private TransactionStatus newStatus;

    @Column(name = "changed_by", nullable = false, length = 50)
    private String changedBy;

    @Column(name = "change_reason", columnDefinition = "TEXT")
    private String changeReason;

    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;
}