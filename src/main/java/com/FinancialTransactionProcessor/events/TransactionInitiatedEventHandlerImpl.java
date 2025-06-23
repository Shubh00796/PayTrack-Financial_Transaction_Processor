package com.FinancialTransactionProcessor.events;

import com.FinancialTransactionProcessor.service_interfaces.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class TransactionInitiatedEventHandlerImpl implements TransactionInitiatedEventHandler {
    private final TransactionService transactionService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void handle(TransactionInitiatedEvent event) {
        transactionService.initiateTransaction(event.getCreateTransactionDTO());
    }
}
