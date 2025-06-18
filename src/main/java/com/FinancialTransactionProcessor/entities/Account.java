package com.FinancialTransactionProcessor.entities;

import com.FinancialTransactionProcessor.enums.AccountStatus;
import com.FinancialTransactionProcessor.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts_for_FTP", indexes = {
        @Index(name = "idx_account_id", columnList = "account_id"),
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_status", columnList = "status"),
        @Index(name = "idx_currency", columnList = "currency_code")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", unique = true, nullable = false, length = 50)
    private String accountId;

    @Column(name = "user_id", nullable = false, length = 50)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false, length = 20)
    private AccountType accountType;

    @Column(name = "currency_code", nullable = false, length = 3)
    @Builder.Default
    private String currencyCode = "USD";

    @Column(name = "balance", precision = 15, scale = 2, nullable = false)
    @Builder.Default
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "available_balance", precision = 15, scale = 2, nullable = false)
    @Builder.Default
    private BigDecimal availableBalance = BigDecimal.ZERO;

    @Column(name = "frozen_amount", precision = 15, scale = 2, nullable = false)
    @Builder.Default
    private BigDecimal frozenAmount = BigDecimal.ZERO;

    @Column(name = "daily_limit", precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal dailyLimit = BigDecimal.valueOf(10000.00);

    @Column(name = "monthly_limit", precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal monthlyLimit = BigDecimal.valueOf(100000.00);

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    @Builder.Default
    private AccountStatus status = AccountStatus.ACTIVE;
}
