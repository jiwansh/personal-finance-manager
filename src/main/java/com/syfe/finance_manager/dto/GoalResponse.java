package com.syfe.finance_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GoalResponse {

    private Long id;
    private String goalName;
    private String targetAmount;
    private String startDate;
    private Object currentProgress;
    private Object progressPercentage;
    private Object remainingAmount;

    public GoalResponse(Long id, String goalName, double targetAmount,
                        double progress, double percentage, double remaining){

        this.id = id;
        this.goalName = goalName;
        this.targetAmount = format(targetAmount);
        this.startDate = null; // Will be set by overloaded constructor
        this.currentProgress = formatOrZero(progress);
        this.progressPercentage = formatPercentOrZero(percentage);
        this.remainingAmount = formatOrZero(remaining);
    }

    public GoalResponse(Long id, String goalName, double targetAmount,
                        String startDate, double progress, double percentage, double remaining){

        this.id = id;
        this.goalName = goalName;
        this.targetAmount = format(targetAmount);
        this.startDate = startDate;
        this.currentProgress = formatOrZero(progress);
        this.progressPercentage = formatPercentOrZero(percentage);
        this.remainingAmount = formatOrZero(remaining);
    }

    private String format(double val){
        return String.format("%.2f", val);
    }

    private Object formatOrZero(double val){
        if(val == 0) return 0;
        return String.format("%.2f", val);
    }

    private Object formatPercentOrZero(double val){
        if(val == 0) return 0.0;
        // Format to 2 decimal places
        return String.format("%.2f", val);
    }
}
