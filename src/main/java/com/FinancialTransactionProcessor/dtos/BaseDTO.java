package com.FinancialTransactionProcessor.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Data
public abstract class BaseDTO {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}