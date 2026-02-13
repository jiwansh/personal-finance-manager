package com.syfe.finance_manager.controller;

import com.syfe.finance_manager.dto.CreateGoalRequest;
import com.syfe.finance_manager.dto.GoalResponse;
import com.syfe.finance_manager.dto.UpdateGoalRequest;
import com.syfe.finance_manager.entity.Goal;
import com.syfe.finance_manager.service.GoalService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    @PostMapping
    public GoalResponse createGoal(@RequestBody CreateGoalRequest request,
                           HttpSession session){

        Long userId = (Long) session.getAttribute("userId");
        return goalService.createGoal(request, userId);
    }
    @GetMapping
    public List<GoalResponse> getGoals(HttpSession session){

        Long userId = (Long) session.getAttribute("userId");
        return goalService.getGoals(userId);
    }
    @GetMapping("/{id}")
    public GoalResponse getGoalById(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        return goalService.getGoalById(id, userId);
    }
    @PutMapping("/{id}")
    public GoalResponse updateGoal(@PathVariable Long id, @RequestBody UpdateGoalRequest request,
                                   HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        return goalService.updateGoal(id, request, userId);
    }

    @DeleteMapping("/{id}")
    public String deleteGoal(@PathVariable Long id, HttpSession session){
        Long userId = (Long) session.getAttribute("userId");
        return goalService.deleteGoal(id, userId);
    }

}
