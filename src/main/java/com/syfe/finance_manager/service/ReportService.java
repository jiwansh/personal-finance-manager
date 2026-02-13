package com.syfe.finance_manager.service;

import com.syfe.finance_manager.exception.BadRequestException;
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

        if (month < 1 || month > 12) {
            throw new BadRequestException("Month must be between 1 and 12");
        }

        List<Object[]> incomeData =
                transactionRepository.getMonthlyIncome(userId, year, month);

        List<Object[]> expenseData =
                transactionRepository.getMonthlyExpense(userId, year, month);

        double totalIncome = 0;
        double totalExpense = 0;

        Map<String, String> incomeMapStr = new HashMap<>();
        Map<String, String> expenseMapStr = new HashMap<>();

        for(Object[] row : incomeData){
            String category = (String) row[0];
            Double amount = (Double) row[1];
            incomeMapStr.put(category, String.format("%.2f", amount));
            totalIncome += amount;
        }

        for(Object[] row : expenseData){
            String category = (String) row[0];
            Double amount = (Double) row[1];
            expenseMapStr.put(category, String.format("%.2f", amount));
            totalExpense += amount;
        }

        double netSavings = totalIncome - totalExpense;
        if(netSavings < 0) netSavings = 0;

        Map<String,Object> result = new HashMap<>();
        result.put("year", year);
        result.put("month", month);
        result.put("totalIncome", incomeMapStr);
        result.put("totalExpenses", expenseMapStr);
        result.put("netSavings", netSavings == 0 ? 0 : String.format("%.2f", netSavings));

        return result;
    }

    public Map<String,Object> getYearlyReport(Long userId, int year){

        List<Object[]> incomeData =
                transactionRepository.getYearlyIncome(userId, year);

        List<Object[]> expenseData =
                transactionRepository.getYearlyExpense(userId, year);

        Map<String, String> incomeMapStr = new HashMap<>();
        Map<String, String> expenseMapStr = new HashMap<>();

        double totalIncome = 0;
        double totalExpense = 0;

        for(Object[] row : incomeData){
            String category = (String) row[0];
            Double amount = (Double) row[1];
            incomeMapStr.put(category, String.format("%.2f", amount));
            totalIncome += amount;
        }

        for(Object[] row : expenseData){
            String category = (String) row[0];
            Double amount = (Double) row[1];
            expenseMapStr.put(category, String.format("%.2f", amount));
            totalExpense += amount;
        }

        double netSavings = totalIncome - totalExpense;
        if (netSavings < 0) netSavings = 0;

        Map<String,Object> result = new HashMap<>();
        result.put("year", year);
        result.put("totalIncome", incomeMapStr);
        result.put("totalExpenses", expenseMapStr);
        result.put("netSavings", netSavings == 0 ? 0 : String.format("%.2f", netSavings));

        return result;
    }
}
