package com.FinancialTransactionProcessor.controllers;

import com.FinancialTransactionProcessor.dtos.CreateUserDTO;
import com.FinancialTransactionProcessor.dtos.UpdateUserDTO;
import com.FinancialTransactionProcessor.dtos.UserResponseDTO;
import com.FinancialTransactionProcessor.enums.UserStatus;
import com.FinancialTransactionProcessor.service_interfaces.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        UserResponseDTO createdUser = userService.createUser(createUserDTO);
        return ResponseEntity.created(URI.create("/api/v1/users/" + createdUser.getUserId())).body(createdUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable @NotBlank String userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<UserResponseDTO>> getUsersByStatus(
            @PathVariable @NotNull UserStatus status,
            Pageable pageable) {
        return ResponseEntity.ok(userService.getUsersByStatus(status, pageable));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable @NotBlank String userId,
            @Valid @RequestBody UpdateUserDTO updateUserDTO) {
        return ResponseEntity.ok(userService.updateUser(userId, updateUserDTO));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable @NotBlank String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{userId}/status")
    public ResponseEntity<Void> updateUserStatus(
            @PathVariable @NotBlank String userId,
            @RequestParam @NotNull UserStatus status) {
        userService.updateUserStatus(userId, status);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/active")
    public ResponseEntity<Boolean> isUserActive(@PathVariable @NotBlank String userId) {
        return ResponseEntity.ok(userService.isUserActive(userId));
    }

    @GetMapping("/{userId}/locked")
    public ResponseEntity<Boolean> isUserLocked(@PathVariable @NotBlank String userId) {
        return ResponseEntity.ok(userService.isUserLocked(userId));
    }

    @PostMapping("/{userId}/lock")
    public ResponseEntity<Void> lockUser(@PathVariable @NotBlank String userId) {
        userService.lockUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/unlock")
    public ResponseEntity<Void> unlockUser(@PathVariable @NotBlank String userId) {
        userService.unlockUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/phone")
    public ResponseEntity<UserResponseDTO> getUserByPhoneNumber(
            @RequestParam @NotBlank String phoneNumber) {
        Optional<UserResponseDTO> user = userService.getUserByPhoneNumber(phoneNumber);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
