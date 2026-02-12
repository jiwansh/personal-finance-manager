package com.syfe.finance_manager.dto;

import lombok.Data;

@Data
public class UpdateTransactionRequest {

    private Double amount;
    private String description;
    private String category;
}
