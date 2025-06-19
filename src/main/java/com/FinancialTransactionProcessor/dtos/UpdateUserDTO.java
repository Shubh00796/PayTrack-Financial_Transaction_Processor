package com.FinancialTransactionProcessor.dtos;

import com.FinancialTransactionProcessor.enums.KycStatus;
import com.FinancialTransactionProcessor.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {

    private String phoneNumber;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private KycStatus kycStatus;
    private UserStatus status;
}
