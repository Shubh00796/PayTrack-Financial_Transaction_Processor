package com.FinancialTransactionProcessor.mappers;

import com.FinancialTransactionProcessor.dtos.AccountResponseDTO;
import com.FinancialTransactionProcessor.dtos.CreateAccountDTO;
import com.FinancialTransactionProcessor.dtos.UpdateAccountDTO;
import com.FinancialTransactionProcessor.entities.Account;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountResponseDTO toDto(Account account);

    Account toEntity(CreateAccountDTO createDto);

    void updateEntity(@MappingTarget Account account, UpdateAccountDTO updateDto);
}
