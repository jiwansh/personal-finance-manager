package com.syfe.finance_manager.service;

import com.syfe.finance_manager.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final TransactionRepository transactionRepository;

    public Map<String,Object> getMonthlyReport(Long userId, int year, int month){

        List<Object[]> incomeData =
                transactionRepository.getMonthlyIncome(userId, year, month);

        List<Object[]> expenseData =
                transactionRepository.getMonthlyExpense(userId, year, month);

        Map<String, Double> incomeMap = new HashMap<>();
        Map<String, Double> expenseMap = new HashMap<>();

        double totalIncome = 0;
        double totalExpense = 0;

        for(Object[] row : incomeData){
            String category = (String) row[0];
            Double amount = (Double) row[1];
            incomeMap.put(category, amount);
            totalIncome += amount;
        }

        for(Object[] row : expenseData){
            String category = (String) row[0];
            Double amount = (Double) row[1];
            expenseMap.put(category, amount);
            totalExpense += amount;
        }

        double netSavings = totalIncome - totalExpense;
        if(netSavings < 0) netSavings = 0;

        String.format("%.2f", netSavings);


        Map<String,Object> result = new HashMap<>();
        result.put("year", year);
        result.put("month", month);
        result.put("totalIncome", incomeMap);
        result.put("totalExpenses", expenseMap);
        result.put("netSavings", netSavings);

        return result;
    }

    public Map<String,Object> getYearlyReport(Long userId, int year){

        List<Object[]> incomeData =
                transactionRepository.getYearlyIncome(userId, year);

        List<Object[]> expenseData =
                transactionRepository.getYearlyExpense(userId, year);

        Map<String, Double> incomeMap = new HashMap<>();
        Map<String, Double> expenseMap = new HashMap<>();

        double totalIncome = 0;
        double totalExpense = 0;

        for(Object[] row : incomeData){
            String category = (String) row[0];
            Double amount = (Double) row[1];
            incomeMap.put(category, amount);
            totalIncome += amount;
        }

        for(Object[] row : expenseData){
            String category = (String) row[0];
            Double amount = (Double) row[1];
            expenseMap.put(category, amount);
            totalExpense += amount;
        }

        double netSavings = totalIncome - totalExpense;

        Map<String,Object> result = new HashMap<>();
        result.put("year", year);
        result.put("totalIncome", incomeMap);
        result.put("totalExpenses", expenseMap);
        result.put("netSavings", netSavings);

        return result;
    }

}
