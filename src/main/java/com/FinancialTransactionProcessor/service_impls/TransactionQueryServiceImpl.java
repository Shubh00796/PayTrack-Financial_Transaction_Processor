package com.FinancialTransactionProcessor.service_impls;

import com.FinancialTransactionProcessor.dtos.TransactionResponseDTO;
import com.FinancialTransactionProcessor.entities.Transaction;
import com.FinancialTransactionProcessor.enums.PaymentMethod;
import com.FinancialTransactionProcessor.enums.TransactionStatus;
import com.FinancialTransactionProcessor.enums.TransactionType;
import com.FinancialTransactionProcessor.mappers.TransactionMapper;
import com.FinancialTransactionProcessor.reposiotry_services.TransactionRepoService;
import com.FinancialTransactionProcessor.service_interfaces.TransactionQueryService;
import com.FinancialTransactionProcessor.validation_utils.TransactionValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionQueryServiceImpl implements TransactionQueryService {

    private final TransactionRepoService repoService;
    private final TransactionMapper mapper;
    private final TransactionValidator validator;

    @Override
    public TransactionResponseDTO getTransactionById(String transactionId) {
        return Optional.ofNullable(repoService.findByTransactionId(transactionId))
                .map(mapper::toDto)
                .orElse(null); // Consider throwing a ResourceNotFoundException if needed
    }

    @Override
    public Page<TransactionResponseDTO> getAllTransactions(Pageable pageable) {
        return repoService.findAll(pageable)
                .map(mapper::toDto);
    }

    @Override
    public Page<TransactionResponseDTO> getTransactionsByStatus(TransactionStatus status, Pageable pageable) {
        return repoService.findByStatus(status, pageable)
                .map(mapper::toDto);
    }

    @Override
    public Page<TransactionResponseDTO> getTransactionsByAccount(String accountId, Pageable pageable) {
        return repoService.findByAccount(accountId, pageable)
                .map(mapper::toDto);
    }

    @Override
    public List<TransactionResponseDTO> getTransactionsByDateRange(TransactionType type, LocalDateTime startDate, LocalDateTime endDate) {
        return mapToDtoList(repoService.findByTypeAndDateRange(type, startDate, endDate));
    }

    @Override
    public List<TransactionResponseDTO> getTransactionsByPaymentMethod(PaymentMethod paymentMethod) {
        return mapToDtoList(repoService.findByPaymentMethod(paymentMethod));
    }

    // 🔒 Private helper method to reduce duplication
    private List<TransactionResponseDTO> mapToDtoList(List<Transaction> transactions) {
        return transactions.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
