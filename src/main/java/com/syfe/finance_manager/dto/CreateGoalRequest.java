package com.syfe.finance_manager.dto;

import lombok.Data;

@Data
public class CreateGoalRequest {

    private String goalName;
    private double targetAmount;
    private String targetDate;
}
