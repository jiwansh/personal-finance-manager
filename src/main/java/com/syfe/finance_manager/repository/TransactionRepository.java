package com.syfe.finance_manager.repository;

import com.syfe.finance_manager.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Map;
import java.util.List;



import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserIdOrderByDateDesc(Long userId);

    @Query("SELECT COALESCE(SUM(t.amount),0) FROM Transaction t " +
            "WHERE t.userId=:userId AND t.type='INCOME' AND t.date>=:startDate")
    double getTotalIncome(@Param("userId") Long userId,
                          @Param("startDate") LocalDate startDate);

    @Query("SELECT COALESCE(SUM(t.amount),0) FROM Transaction t " +
            "WHERE t.userId=:userId AND t.type='EXPENSE' AND t.date>=:startDate")
    double getTotalExpense(@Param("userId") Long userId,
                           @Param("startDate") LocalDate startDate);

    @Query("SELECT t.category, SUM(t.amount) FROM Transaction t " +
            "WHERE t.userId=:userId AND t.type='INCOME' AND YEAR(t.date)=:year AND MONTH(t.date)=:month " +
            "GROUP BY t.category")
    List<Object[]> getMonthlyIncome(@Param("userId") Long userId,
                                    @Param("year") int year,
                                    @Param("month") int month);

    @Query("SELECT t.category, SUM(t.amount) FROM Transaction t " +
            "WHERE t.userId=:userId AND t.type='EXPENSE' AND YEAR(t.date)=:year AND MONTH(t.date)=:month " +
            "GROUP BY t.category")
    List<Object[]> getMonthlyExpense(@Param("userId") Long userId,
                                     @Param("year") int year,
                                     @Param("month") int month);


    @Query("SELECT t.category, SUM(t.amount) FROM Transaction t " +
            "WHERE t.userId=:userId AND t.type='INCOME' AND YEAR(t.date)=:year " +
            "GROUP BY t.category")
    List<Object[]> getYearlyIncome(@Param("userId") Long userId,
                                   @Param("year") int year);

    @Query("SELECT t.category, SUM(t.amount) FROM Transaction t " +
            "WHERE t.userId=:userId AND t.type='EXPENSE' AND YEAR(t.date)=:year " +
            "GROUP BY t.category")
    List<Object[]> getYearlyExpense(@Param("userId") Long userId,
                                    @Param("year") int year);


}
