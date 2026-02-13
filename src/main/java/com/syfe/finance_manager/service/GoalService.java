package com.syfe.finance_manager.service;

import com.syfe.finance_manager.dto.CreateGoalRequest;
import com.syfe.finance_manager.dto.GoalResponse;
import com.syfe.finance_manager.dto.UpdateGoalRequest;
import com.syfe.finance_manager.entity.Goal;
import com.syfe.finance_manager.exception.ForbiddenException;
import com.syfe.finance_manager.exception.NotFoundException;
import com.syfe.finance_manager.repository.GoalRepository;
import com.syfe.finance_manager.repository.TransactionRepository;

import com.syfe.finance_manager.exception.BadRequestException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;
    private final TransactionRepository transactionRepository;

    // CREATE GOAL
    public GoalResponse createGoal(CreateGoalRequest request, Long userId){

        // validation
        if(request.getTargetAmount() <= 0){
            throw new BadRequestException("Target amount must be greater than zero");
        }

        LocalDate targetDate = LocalDate.parse(request.getTargetDate());

        if(targetDate.isBefore(LocalDate.now())){
            throw new BadRequestException("Target date must be in the future");
        }

        Goal goal = Goal.builder()
                .goalName(request.getGoalName())
                .targetAmount(request.getTargetAmount())
                .targetDate(targetDate)
                .startDate(LocalDate.now())
                .userId(userId)
                .build();

        Goal savedGoal = goalRepository.save(goal);
        return getGoalById(savedGoal.getId(), userId);
    }

    // GET GOALS WITH PROGRESS
    public List<GoalResponse> getGoals(Long userId){

        List<Goal> goals = goalRepository.findByUserId(userId);

        return goals.stream().map(goal -> {
            // Calculate progress based on transactions on or after the goal's startDate
            LocalDate startDate = goal.getStartDate();
            if (startDate == null) {
                // If no startDate, count all transactions
                startDate = LocalDate.MIN;
            }
            
            Double incomeResult = transactionRepository.getTotalIncomeAfterDate(userId, startDate);
            double income = incomeResult != null ? incomeResult : 0.0;

            Double expenseResult = transactionRepository.getTotalExpenseAfterDate(userId, startDate);
            double expense = expenseResult != null ? expenseResult : 0.0;

            double progress = income - expense;
            if(progress < 0) progress = 0;

            double percentage = (progress / goal.getTargetAmount()) * 100;
            if(percentage < 0) percentage = 0;

            double remaining = goal.getTargetAmount() - progress;
            if(remaining < 0) remaining = 0;


            return new GoalResponse(
                    goal.getId(),
                    goal.getGoalName(),
                    goal.getTargetAmount(),
                    goal.getStartDate() != null ? goal.getStartDate().toString() : null,
                    progress,
                    percentage,
                    remaining
            );
        }).toList();
    }

    public GoalResponse getGoalById(Long goalId, Long userId) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new NotFoundException("Goal not found"));
        if (!goal.getUserId().equals(userId)) {
            throw new ForbiddenException("You cannot access this goal");
        }
        // Calculate progress based on transactions on or after the goal's startDate
        LocalDate startDate = goal.getStartDate();
        if (startDate == null) {
            // If no startDate, count all transactions
            startDate = LocalDate.MIN;
        }
        
        Double incomeResult = transactionRepository.getTotalIncomeAfterDate(userId, startDate);
        double income = incomeResult != null ? incomeResult : 0.0;
        
        Double expenseResult = transactionRepository.getTotalExpenseAfterDate(userId, startDate);
        double expense = expenseResult != null ? expenseResult : 0.0;
        
        double progress = income - expense;
        if (progress < 0) progress = 0;
        double percentage = (progress / goal.getTargetAmount()) * 100;
        if (percentage < 0) percentage = 0;
        double remaining = goal.getTargetAmount() - progress;
        if (remaining < 0) remaining = 0;
        return new GoalResponse(goal.getId(), goal.getGoalName(), goal.getTargetAmount(),
                goal.getStartDate() != null ? goal.getStartDate().toString() : null,
                progress, percentage, remaining);
    }

    public GoalResponse updateGoal(Long goalId, UpdateGoalRequest request, Long userId) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new NotFoundException("Goal not found"));
        if (!goal.getUserId().equals(userId)) {
            throw new ForbiddenException("You cannot access this goal");
        }
        if (request.getTargetAmount() != null) {
            if (request.getTargetAmount() <= 0) {
                throw new BadRequestException("Target amount must be greater than zero");
            }
            goal.setTargetAmount(request.getTargetAmount());
        }
        if (request.getTargetDate() != null && !request.getTargetDate().isEmpty()) {
            try {
                LocalDate targetDate = LocalDate.parse(request.getTargetDate());
                if (targetDate.isBefore(LocalDate.now())) {
                    throw new BadRequestException("Target date must be in the future");
                }
                goal.setTargetDate(targetDate);
            } catch (DateTimeParseException e) {
                throw new BadRequestException("Invalid date format. Use yyyy-MM-dd");
            }
        }
        goalRepository.save(goal);
        return getGoalById(goalId, userId);
    }

    //Delete Goals
    public String deleteGoal(Long goalId, Long userId){

        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new NotFoundException("Goal not found"));

        if(!goal.getUserId().equals(userId)){
            throw new ForbiddenException("You cannot access this goal");
        }

        goalRepository.delete(goal);

        return "Goal deleted successfully";
    }

}
