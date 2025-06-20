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

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public UserResponseDTO createUser(CreateUserDTO createUserDTO) {
        User user = mapper.toEntity(createUserDTO);


        user.setUserId(createUserDTO.getUserId()); // Manually set the user_id field
        return mapper.toDto(repoService.save(user));
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public UserResponseDTO getUserById(String userId) {
        User user = getUserOrThrow(userId);
        return mapper.toDto(user);
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
    public UserResponseDTO updateUser(String userId, UpdateUserDTO updateUserDTO) {
        User user = getUserOrThrow(userId);
        user.setCreatedAt(LocalDateTime.now());
        mapper.updateEntity(user, updateUserDTO);
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
        user.setCreatedAt(LocalDateTime.now());
        user.setStatus(status);
        repoService.update(user);
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public boolean isUserActive(String userId) {
        return UserStatus.ACTIVE.equals(getUserOrThrow(userId).getStatus());
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public boolean isUserLocked(String userId) {
        return UserStatus.LOCKED.equals(getUserOrThrow(userId).getStatus());
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
        return Optional.ofNullable(mapper.toDto(repoService.getByPhoneNumber(phoneNumber)));
    }

    private User getUserOrThrow(String userId) {
        return repoService.findByUserId(userId);
    }
}