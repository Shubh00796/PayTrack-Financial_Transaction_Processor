package com.FinancialTransactionProcessor.dtos;

import com.FinancialTransactionProcessor.enums.KycStatus;
import com.FinancialTransactionProcessor.enums.UserStatus;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "User ID is required")
    private String userId;

    @PastOrPresent(message = "CreatedAt cannot be in the future")
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PastOrPresent(message = "UpdatedAt cannot be in the future")
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false, updatable = false)
    private LocalDateTime updatedAt;

    private String phoneNumber;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    private KycStatus kycStatus;

    private UserStatus status;


}
