package com.FinancialTransactionProcessor.mappers;

import com.FinancialTransactionProcessor.dtos.TransactionResponseDTO;
import com.FinancialTransactionProcessor.dtos.CreateTransactionDTO;
import com.FinancialTransactionProcessor.dtos.UpdateTransactionDTO;
import com.FinancialTransactionProcessor.entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionResponseDTO toDto(Transaction transaction);

    Transaction toEntity(CreateTransactionDTO createDto);

    void updateEntity(@MappingTarget Transaction transaction, UpdateTransactionDTO updateDto);
}