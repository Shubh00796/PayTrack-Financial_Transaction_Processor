package com.FinancialTransactionProcessor.mappers;

import com.FinancialTransactionProcessor.dtos.NotificationResponseDTO;
import com.FinancialTransactionProcessor.dtos.CreateNotificationDTO;
import com.FinancialTransactionProcessor.dtos.UpdateNotificationDTO;
import com.FinancialTransactionProcessor.entities.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationResponseDTO toDto(Notification notification);

    Notification toEntity(CreateNotificationDTO createDto);

    void updateEntity(@MappingTarget Notification notification, UpdateNotificationDTO updateDto);
}