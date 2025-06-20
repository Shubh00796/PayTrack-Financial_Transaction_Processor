package com.FinancialTransactionProcessor.mappers;

import com.FinancialTransactionProcessor.dtos.TransactionAuditResponseDTO;
import com.FinancialTransactionProcessor.dtos.CreateTransactionAuditDTO;
import com.FinancialTransactionProcessor.dtos.UpdateTransactionAuditDTO;
import com.FinancialTransactionProcessor.entities.TransactionAudit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TransactionAuditMapper {

    TransactionAuditResponseDTO toDto(TransactionAudit transactionAudit);

    TransactionAudit toEntity(CreateTransactionAuditDTO createDto);

    void updateEntity(@MappingTarget TransactionAudit transactionAudit, UpdateTransactionAuditDTO updateDto);
}