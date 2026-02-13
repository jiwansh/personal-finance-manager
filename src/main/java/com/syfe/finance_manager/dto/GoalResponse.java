package com.syfe.finance_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GoalResponse {

    private Long id;
    private String goalName;
    private String targetAmount;
    private String currentProgress;
    private String progressPercentage;
    private String remainingAmount;

    public GoalResponse(Long id, String goalName, double targetAmount,
                        double progress, double percentage, double remaining){

        this.id = id;
        this.goalName = goalName;
        this.targetAmount = format(targetAmount);
        this.currentProgress = format(progress);
        this.progressPercentage = formatPercent(percentage);
        this.remainingAmount = format(remaining);
    }

    private String format(double val){
        if(val == 0) return "0";
        return String.format("%.2f", val);
    }

    private String formatPercent(double val){
        if(val == 0) return "0.0";
        return String.format("%.1f", val);
    }
}
