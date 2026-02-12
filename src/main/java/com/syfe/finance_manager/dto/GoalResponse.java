package com.syfe.finance_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GoalResponse {

    private Long id;
    private String goalName;
    private double targetAmount;
    private double currentProgress;
    private double progressPercentage;
    private double remainingAmount;
}
