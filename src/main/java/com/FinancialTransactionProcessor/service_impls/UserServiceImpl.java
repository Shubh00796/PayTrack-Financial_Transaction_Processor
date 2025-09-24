package com.FinancialTransactionProcessor.service_impls;

import com.FinancialTransactionProcessor.dtos.CreateUserDTO;
import com.FinancialTransactionProcessor.dtos.UpdateUserDTO;
import com.FinancialTransactionProcessor.dtos.UserResponseDTO;
import com.FinancialTransactionProcessor.entities.User;
import com.FinancialTransactionProcessor.enums.UserStatus;
import com.FinancialTransactionProcessor.exceptions_handling.ResourceNotFoundException;
import com.FinancialTransactionProcessor.mappers.UserMapper;
import com.FinancialTransactionProcessor.reposiotry_services.UserRepoService;
import com.FinancialTransactionProcessor.service_interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepoService repoService;
    private final UserMapper mapper;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public UserResponseDTO createUser(CreateUserDTO dto) {
        User user = mapper.toEntity(dto);
        user.setUserId(dto.getUserId());
        user.setCreatedAt(LocalDateTime.now());
        return mapper.toDto(repoService.save(user));
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public UserResponseDTO getUserById(String userId) {
        return mapper.toDto(getUserOrThrow(userId));
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        return repoService.findAll(pageable).map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public Page<UserResponseDTO> getUsersByStatus(UserStatus status, Pageable pageable) {
        return repoService.findByStatus(status, pageable).map(mapper::toDto);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public UserResponseDTO updateUser(String userId, UpdateUserDTO dto) {
        User user = getUserOrThrow(userId);
        mapper.updateEntity(user, dto);
        user.setUpdatedAt(LocalDateTime.now());
        return mapper.toDto(repoService.update(user));
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteUser(String userId) {
        repoService.deleteById(userId);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void updateUserStatus(String userId, UserStatus status) {
        User user = getUserOrThrow(userId);
        user.setStatus(status);
        user.setUpdatedAt(LocalDateTime.now());
        repoService.update(user);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public boolean isUserActive(String userId) {
        return isUserStatus(userId, UserStatus.ACTIVE);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public boolean isUserLocked(String userId) {
        return isUserStatus(userId, UserStatus.LOCKED);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void lockUser(String userId) {
        updateUserStatus(userId, UserStatus.LOCKED);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void unlockUser(String userId) {
        updateUserStatus(userId, UserStatus.ACTIVE);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public Optional<UserResponseDTO> getUserByPhoneNumber(String phoneNumber) {
        return Optional.ofNullable(repoService.getByPhoneNumber(phoneNumber))
                .map(mapper::toDto);
    }

    // 🔒 Private helper methods

    private User getUserOrThrow(String userId) {
        User user = repoService.findByUserId(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User not found: " + userId);
        }
        return user;
    }

    private boolean isUserStatus(String userId, UserStatus expectedStatus) {
        return expectedStatus.equals(getUserOrThrow(userId).getStatus());
    }
}
