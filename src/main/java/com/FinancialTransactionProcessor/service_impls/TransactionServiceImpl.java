package com.FinancialTransactionProcessor.service_impls;

import com.FinancialTransactionProcessor.dtos.CreateTransactionDTO;
import com.FinancialTransactionProcessor.dtos.TransactionResponseDTO;
import com.FinancialTransactionProcessor.enums.TransactionStatus;
import com.FinancialTransactionProcessor.enums.TransactionType;
import com.FinancialTransactionProcessor.events.EventPublisher;
import com.FinancialTransactionProcessor.mappers.TransactionMapper;
import com.FinancialTransactionProcessor.reposiotry_services.TransactionRepoService;
import com.FinancialTransactionProcessor.service_interfaces.AccountService;
import com.FinancialTransactionProcessor.service_interfaces.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepoService repoService;
    private final TransactionMapper mapper;
    private final AccountService accountService;
    private EventPublisher eventPublisher;


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public TransactionResponseDTO initiateTransaction(CreateTransactionDTO createTransactionDTO) {

        return null;
    }

    @Override
    public void cancelTransaction(String transactionId) {

    }

    @Override
    public void reverseTransaction(String transactionId) {

    }

    @Override
    public void processTransaction(String transactionId) {

    }

    @Override
    public void updateTransactionStatus(String transactionId, TransactionStatus status) {

    }

    @Override
    public void updateTransactionType(String transactionId, TransactionType type) {

    }

    @Override
    public void refundTransaction(String transactionId) {

    }

    @Override
    public void chargebackTransaction(String transactionId) {

    }

    @Override
    public void transferFunds(String fromAccountId, String toAccountId, BigDecimal amount) {

    }

    @Override
    public void depositFunds(String accountId, BigDecimal amount) {

    }

    @Override
    public void withdrawFunds(String accountId, BigDecimal amount) {

    }
}
