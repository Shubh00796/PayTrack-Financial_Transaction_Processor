package com.FinancialTransactionProcessor.dtos;

import com.FinancialTransactionProcessor.enums.KycStatus;
import com.FinancialTransactionProcessor.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO  {

    private String userId;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private KycStatus kycStatus;
    private UserStatus status;


}