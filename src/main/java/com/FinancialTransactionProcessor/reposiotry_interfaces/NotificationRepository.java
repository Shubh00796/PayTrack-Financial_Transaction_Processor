package com.FinancialTransactionProcessor.reposiotry_interfaces;


import com.FinancialTransactionProcessor.entities.Notification;
import com.FinancialTransactionProcessor.enums.NotificationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByStatus(NotificationStatus status);
    Page<Notification> findAll(Pageable pageable);
    @Query("SELECT n FROM Notification n WHERE n.notificationId = :notificationId")
    Optional<Notification> findByNotificationId(@Param("notificationId") String notificationId);
    @Query("SELECT n FROM Notification n WHERE n.userId = :userId")
    List<Notification> findByUserId(@Param("userId") String userId);
}
