package com.syfe.finance_manager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    private LocalDate date;

    private String description;

    private String category; // Salary, Food etc

    @Enumerated(EnumType.STRING)
    private Type type; // INCOME / EXPENSE

    private Long userId;

    public enum Type{
        INCOME,
        EXPENSE
    }
}
