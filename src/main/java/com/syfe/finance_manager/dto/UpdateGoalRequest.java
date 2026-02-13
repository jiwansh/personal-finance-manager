package com.syfe.finance_manager.dto;

import lombok.Data;

@Data
public class UpdateGoalRequest {
    private Double targetAmount;
    private String targetDate;
}

