package com.FinancialTransactionProcessor.reposiotry_interfaces;

import com.FinancialTransactionProcessor.entities.User;
import com.FinancialTransactionProcessor.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);

    Optional<User> findByEmail(String email);

    Page<User> findByStatus(UserStatus status, Pageable pageable);

    Page<User> findAll(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.phoneNumber = :phoneNumber")
    Optional<User> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    boolean existsByUserId(String userId);
}
