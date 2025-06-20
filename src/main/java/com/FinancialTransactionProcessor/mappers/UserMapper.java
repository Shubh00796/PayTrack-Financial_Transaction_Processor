package com.FinancialTransactionProcessor.mappers;

import com.FinancialTransactionProcessor.dtos.UserResponseDTO;
import com.FinancialTransactionProcessor.dtos.CreateUserDTO;
import com.FinancialTransactionProcessor.dtos.UpdateUserDTO;
import com.FinancialTransactionProcessor.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {



    UserResponseDTO toDto(User user);

    User toEntity(CreateUserDTO createDto);

    void updateEntity(@MappingTarget User user, UpdateUserDTO updateDto);
}