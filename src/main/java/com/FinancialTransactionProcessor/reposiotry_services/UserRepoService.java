package com.FinancialTransactionProcessor.reposiotry_services;

import com.FinancialTransactionProcessor.entities.User;
import com.FinancialTransactionProcessor.enums.UserStatus;
import com.FinancialTransactionProcessor.exceptions_handling.ResourceNotFoundException;
import com.FinancialTransactionProcessor.reposiotry_interfaces.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRepoService {

    private final UserRepository userRepository;

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found"));
    }

    public Page<User> findByStatus(UserStatus status, Pageable pageable) {
        return userRepository.findByStatus(status, pageable);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean existsByUserId(String userId) {
        return userRepository.existsByUserId(userId);
    }


    public User getByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResourceNotFoundException("User with phone number " + phoneNumber + " not found"));
    }


    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public void deleteById(String userId) {
        User user = findByUserId(userId);
        userRepository.delete(user);
    }
}
