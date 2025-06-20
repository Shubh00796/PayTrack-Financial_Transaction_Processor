package com.FinancialTransactionProcessor.service_interfaces;


import com.FinancialTransactionProcessor.dtos.CreateUserDTO;
import com.FinancialTransactionProcessor.dtos.UpdateUserDTO;
import com.FinancialTransactionProcessor.dtos.UserResponseDTO;
import com.FinancialTransactionProcessor.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserResponseDTO createUser(CreateUserDTO createUserDTO);

    UserResponseDTO getUserById(String userId);

    Page<UserResponseDTO> getAllUsers(Pageable pageable);

    Page<UserResponseDTO> getUsersByStatus(UserStatus status, Pageable pageable);

    UserResponseDTO updateUser(String userId, UpdateUserDTO updateUserDTO);

    void deleteUser(String userId);

    void updateUserStatus(String userId, UserStatus status);

    boolean isUserActive(String userId);

    boolean isUserLocked(String userId);

    void lockUser(String userId);

    void unlockUser(String userId);

    Optional<UserResponseDTO> getUserByPhoneNumber(String phoneNumber);
}
