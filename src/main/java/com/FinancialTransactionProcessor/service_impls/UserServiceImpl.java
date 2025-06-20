package com.FinancialTransactionProcessor.service_impls;

import com.FinancialTransactionProcessor.dtos.CreateUserDTO;
import com.FinancialTransactionProcessor.dtos.UpdateUserDTO;
import com.FinancialTransactionProcessor.dtos.UserResponseDTO;
import com.FinancialTransactionProcessor.entities.User;
import com.FinancialTransactionProcessor.enums.UserStatus;
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

    /**
     * Creates a new user.
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public UserResponseDTO createUser(CreateUserDTO createUserDTO) {
        User user = mapper.toEntity(createUserDTO);
        user.setUserId(createUserDTO.getUserId());
        user.setCreatedAt(LocalDateTime.now());
        return mapper.toDto(repoService.save(user));
    }

    /**
     * Retrieves a user by ID.
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public UserResponseDTO getUserById(String userId) {
        return mapper.toDto(getUserOrThrow(userId));
    }

    /**
     * Retrieves all users with pagination.
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        return repoService.findAll(pageable).map(mapper::toDto);
    }

    /**
     * Retrieves users by status with pagination.
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public Page<UserResponseDTO> getUsersByStatus(UserStatus status, Pageable pageable) {
        return repoService.findByStatus(status, pageable).map(mapper::toDto);
    }

    /**
     * Updates user details.
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public UserResponseDTO updateUser(String userId, UpdateUserDTO updateUserDTO) {
        User user = getUserOrThrow(userId);
        mapper.updateEntity(user, updateUserDTO);
        user.setUpdatedAt(LocalDateTime.now());
        return mapper.toDto(repoService.update(user));
    }

    /**
     * Deletes a user by ID.
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteUser(String userId) {
        repoService.deleteById(userId);
    }

    /**
     * Updates the status of a user.
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void updateUserStatus(String userId, UserStatus status) {
        User user = getUserOrThrow(userId);
        user.setStatus(status);
        user.setUpdatedAt(LocalDateTime.now());
        repoService.update(user);
    }

    /**
     * Checks if a user is active.
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public boolean isUserActive(String userId) {
        return UserStatus.ACTIVE.equals(getUserOrThrow(userId).getStatus());
    }

    /**
     * Checks if a user is locked.
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public boolean isUserLocked(String userId) {
        return UserStatus.LOCKED.equals(getUserOrThrow(userId).getStatus());
    }

    /**
     * Locks a user.
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void lockUser(String userId) {
        updateUserStatus(userId, UserStatus.LOCKED);
    }

    /**
     * Unlocks a user.
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void unlockUser(String userId) {
        updateUserStatus(userId, UserStatus.ACTIVE);
    }

    /**
     * Retrieves a user by phone number.
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public Optional<UserResponseDTO> getUserByPhoneNumber(String phoneNumber) {
        return Optional.ofNullable(mapper.toDto(repoService.getByPhoneNumber(phoneNumber)));
    }

    /**
     * Helper method to fetch user or throw exception.
     */
    private User getUserOrThrow(String userId) {
        return repoService.findByUserId(userId);
    }
}
