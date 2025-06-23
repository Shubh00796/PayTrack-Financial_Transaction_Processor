package com.FinancialTransactionProcessor.events;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPublisherImpl implements EventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(TransactionInitiatedEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
