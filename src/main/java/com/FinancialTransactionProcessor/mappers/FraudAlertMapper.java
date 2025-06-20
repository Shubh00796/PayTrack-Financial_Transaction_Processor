package com.FinancialTransactionProcessor.mappers;

import com.FinancialTransactionProcessor.dtos.FraudAlertResponseDTO;
import com.FinancialTransactionProcessor.dtos.CreateFraudAlertDTO;
import com.FinancialTransactionProcessor.dtos.UpdateFraudAlertDTO;
import com.FinancialTransactionProcessor.entities.FraudAlert;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FraudAlertMapper {

    FraudAlertResponseDTO toDto(FraudAlert fraudAlert);

    FraudAlert toEntity(CreateFraudAlertDTO createDto);

    void updateEntity(@MappingTarget FraudAlert fraudAlert, UpdateFraudAlertDTO updateDto);
}
