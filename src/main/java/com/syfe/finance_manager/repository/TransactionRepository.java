package com.syfe.finance_manager.repository;

import com.syfe.finance_manager.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserIdOrderByDateDesc(Long userId);

    // MONTHLY INCOME (ignoring negative & zero)
    @Query("SELECT t.category, SUM(t.amount) FROM Transaction t " +
            "WHERE t.userId=:userId AND t.type='INCOME' AND t.amount>0 " +
            "AND YEAR(t.date)=:year AND MONTH(t.date)=:month " +
            "GROUP BY t.category")
    List<Object[]> getMonthlyIncome(@Param("userId") Long userId,
                                    @Param("year") int year,
                                    @Param("month") int month);

    // MONTHLY EXPENSE
    @Query("SELECT t.category, SUM(t.amount) FROM Transaction t " +
            "WHERE t.userId=:userId AND t.type='EXPENSE' AND t.amount>0 " +
            "AND YEAR(t.date)=:year AND MONTH(t.date)=:month " +
            "GROUP BY t.category")
    List<Object[]> getMonthlyExpense(@Param("userId") Long userId,
                                     @Param("year") int year,
                                     @Param("month") int month);

    // YEARLY INCOME
    @Query("SELECT t.category, SUM(t.amount) FROM Transaction t " +
            "WHERE t.userId=:userId AND t.type='INCOME' AND t.amount>0 " +
            "AND YEAR(t.date)=:year GROUP BY t.category")
    List<Object[]> getYearlyIncome(@Param("userId") Long userId,
                                   @Param("year") int year);

    // YEARLY EXPENSE
    @Query("SELECT t.category, SUM(t.amount) FROM Transaction t " +
            "WHERE t.userId=:userId AND t.type='EXPENSE' AND t.amount>0 " +
            "AND YEAR(t.date)=:year GROUP BY t.category")
    List<Object[]> getYearlyExpense(@Param("userId") Long userId,
                                    @Param("year") int year);

    List<Transaction> findByUserIdAndCategoryOrderByDateDesc(Long userId, String category);

    long countByUserIdAndCategory(Long userId, String category);

    List<Transaction> findByUserIdAndDateBetweenOrderByDateDesc(Long userId,
                                                                LocalDate start,
                                                                LocalDate end);

    // GOAL PROGRESS CALCULATION
    @Query("SELECT COALESCE(SUM(t.amount),0) FROM Transaction t " +
            "WHERE t.userId=:userId AND t.type='INCOME' AND t.amount>0 AND t.date>=:date")
    Double getTotalIncomeAfterDate(@Param("userId") Long userId,
                                   @Param("date") LocalDate date);

    @Query("SELECT COALESCE(SUM(t.amount),0) FROM Transaction t " +
            "WHERE t.userId=:userId AND t.type='EXPENSE' AND t.amount>0 AND t.date>=:date")
    Double getTotalExpenseAfterDate(@Param("userId") Long userId,
                                    @Param("date") LocalDate date);

    // Get all income/expense for goal progress (no date filter)
    @Query("SELECT COALESCE(SUM(t.amount),0) FROM Transaction t " +
            "WHERE t.userId=:userId AND t.type='INCOME' AND t.amount>0")
    Double getTotalIncome(@Param("userId") Long userId);

    @Query("SELECT COALESCE(SUM(t.amount),0) FROM Transaction t " +
            "WHERE t.userId=:userId AND t.type='EXPENSE' AND t.amount>0")
    Double getTotalExpense(@Param("userId") Long userId);
}
