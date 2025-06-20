package com.FinancialTransactionProcessor.reposiotry_interfaces;

import com.FinancialTransactionProcessor.entities.Account;
import com.FinancialTransactionProcessor.enums.AccountStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountId(String accountId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Account a WHERE a.accountId = :accountId")
    Optional<Account> findByAccountIdWithLock(@Param("accountId") String accountId);

    Page<Account> findByUserId(String userId, Pageable pageable);

    Optional<Account> findByUserId(String userId);


    Page<Account> findByStatus(AccountStatus status, Pageable pageable);

    Page<Account> findByUserIdAndStatus(String userId, AccountStatus status, Pageable pageable);

    @Query("SELECT a FROM Account a WHERE a.userId = :userId AND a.status = :status AND a.currencyCode = :currency")
    Page<Account> findByUserIdAndStatusAndCurrency(
            @Param("userId") String userId,
            @Param("status") AccountStatus status,
            @Param("currency") String currency,
            Pageable pageable
    );

    @Modifying
    @Query("UPDATE Account a SET a.balance = a.balance + :amount, a.availableBalance = a.availableBalance + :amount WHERE a.accountId = :accountId")
    int updateBalance(@Param("accountId") String accountId, @Param("amount") BigDecimal amount);

    @Query("SELECT COUNT(a) > 0 FROM Account a WHERE a.accountId = :accountId AND a.availableBalance >= :amount")
    boolean hasSufficientBalance(@Param("accountId") String accountId, @Param("amount") BigDecimal amount);

    @Query("SELECT a FROM Account a WHERE a.accountType = :accountType")
    List<Account> findByAccountType(@Param("accountType") String accountType);

    @Query("SELECT a FROM Account a WHERE a.currencyCode = :currencyCode")
    List<Account> findByCurrencyCode(@Param("currencyCode") String currencyCode);
}