package com.syfe.finance_manager.dto;

import lombok.Data;

@Data
public class CreateTransactionRequest {

    private double amount;
    private String date;     // yyyy-mm-dd
    private String category;
    private String description;
}
