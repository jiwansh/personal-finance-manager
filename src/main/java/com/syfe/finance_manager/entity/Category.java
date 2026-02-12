package com.syfe.finance_manager.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;   // Salary, Food etc

    @Enumerated(EnumType.STRING)
    private Type type;     // INCOME or EXPENSE

    private boolean isCustom;

    private Long userId;   // null for default categories

    public enum Type {
        INCOME,
        EXPENSE
    }
}
