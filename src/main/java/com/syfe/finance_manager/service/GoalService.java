package com.syfe.finance_manager.service;

import com.syfe.finance_manager.dto.CreateGoalRequest;
import com.syfe.finance_manager.dto.GoalResponse;
import com.syfe.finance_manager.entity.Goal;
import com.syfe.finance_manager.exception.ForbiddenException;
import com.syfe.finance_manager.exception.NotFoundException;
import com.syfe.finance_manager.repository.GoalRepository;
import com.syfe.finance_manager.repository.TransactionRepository;

import com.syfe.finance_manager.exception.BadRequestException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;
    private final TransactionRepository transactionRepository;

    // CREATE GOAL
    public Goal createGoal(CreateGoalRequest request, Long userId){

        LocalDate targetDate = LocalDate.parse(request.getTargetDate());

        // validation
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

        return goalRepository.save(goal);
    }

    // GET GOALS WITH PROGRESS
    public List<GoalResponse> getGoals(Long userId){

        List<Goal> goals = goalRepository.findByUserId(userId);

        return goals.stream().map(goal -> {

            double income = transactionRepository
                    .getTotalIncomeAfterDate(userId, goal.getStartDate());

            double expense = transactionRepository
                    .getTotalExpenseAfterDate(userId, goal.getStartDate());

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
                    progress,
                    percentage,
                    remaining
            );
        }).toList();
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
