package com.FinancialTransactionProcessor.controllers;

import com.FinancialTransactionProcessor.dtos.AccountResponseDTO;
import com.FinancialTransactionProcessor.dtos.CreateAccountDTO;
import com.FinancialTransactionProcessor.dtos.UpdateAccountDTO;
import com.FinancialTransactionProcessor.enums.AccountStatus;
import com.FinancialTransactionProcessor.enums.AccountType;
import com.FinancialTransactionProcessor.service_interfaces.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@Valid @RequestBody CreateAccountDTO requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(requestDto));
    }

    @GetMapping
    public ResponseEntity<Page<AccountResponseDTO>> getAllAccounts(Pageable pageable) {
        return ResponseEntity.ok(accountService.getAllAccounts(pageable));
    }


    @GetMapping("{accountId}")
    public ResponseEntity<AccountResponseDTO> getAccountById(@PathVariable @NotBlank String accountId) {
        return ResponseEntity.ok(accountService.getAccountById(accountId));
    }

    @GetMapping(params = "status")
    public ResponseEntity<Page<AccountResponseDTO>> getAccountsByStatus(
            @RequestParam @NotNull AccountStatus status,
            Pageable pageable) {
        return ResponseEntity.ok(accountService.getAccountsByStatus(status, pageable));
    }

    @GetMapping(params = "userId")
    public ResponseEntity<Page<AccountResponseDTO>> getAccountsByUser(
            @RequestParam @NotBlank String userId,
            Pageable pageable) {
        return ResponseEntity.ok(accountService.getAccountsByUser(userId, pageable));
    }

    @PutMapping("{accountId}")
    public ResponseEntity<AccountResponseDTO> updateAccount(
            @PathVariable @NotBlank String accountId,
            @Valid @RequestBody UpdateAccountDTO requestDto) {
        return ResponseEntity.ok(accountService.updateAccount(accountId, requestDto));
    }

    @DeleteMapping("{accountId}")
    public ResponseEntity<Void> closeAccount(@PathVariable @NotBlank String accountId) {
        accountService.closeAccount(accountId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{accountId}/freeze")
    public ResponseEntity<Void> freezeAccount(@PathVariable @NotBlank String accountId) {
        accountService.freezeAccount(accountId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{accountId}/unfreeze")
    public ResponseEntity<Void> unfreezeAccount(@PathVariable @NotBlank String accountId) {
        accountService.unfreezeAccount(accountId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{accountId}/has-sufficient-balance")
    public ResponseEntity<Boolean> hasSufficientBalance(
            @PathVariable @NotBlank String accountId,
            @RequestParam @DecimalMin(value = "0.0", inclusive = false) BigDecimal amount) {
        return ResponseEntity.ok(accountService.hasSufficientBalance(accountId, amount));
    }

    @PostMapping("{accountId}/update-daily-limit")
    public ResponseEntity<Void> updateDailyLimit(
            @PathVariable @NotBlank String accountId,
            @RequestParam @DecimalMin(value = "0.0", inclusive = false) BigDecimal dailyLimit) {
        accountService.updateDailyLimit(accountId, dailyLimit);
        return ResponseEntity.ok().build();
    }

    @PostMapping("{accountId}/update-monthly-limit")
    public ResponseEntity<Void> updateMonthlyLimit(
            @PathVariable @NotBlank String accountId,
            @RequestParam @DecimalMin(value = "0.0", inclusive = false) BigDecimal monthlyLimit) {
        accountService.updateMonthlyLimit(accountId, monthlyLimit);
        return ResponseEntity.ok().build();
    }

    @GetMapping(params = "currency")
    public ResponseEntity<List<AccountResponseDTO>> getAccountsByCurrency(
            @RequestParam @NotBlank String currency) {
        return ResponseEntity.ok(accountService.getAccountsByCurrency(currency));
    }

    @GetMapping(params = "type")
    public ResponseEntity<List<AccountResponseDTO>> getAccountsByAccountType(
            @RequestParam @NotNull AccountType type) {
        return ResponseEntity.ok(accountService.getAccountsByAccountType(type));
    }

    @GetMapping("{accountId}/is-active")
    public ResponseEntity<Boolean> isAccountActive(@PathVariable @NotBlank String accountId) {
        return ResponseEntity.ok(accountService.isAccountActive(accountId));
    }

    @GetMapping("{accountId}/is-frozen")
    public ResponseEntity<Boolean> isAccountFrozen(@PathVariable @NotBlank String accountId) {
        return ResponseEntity.ok(accountService.isAccountFrozen(accountId));
    }
}
