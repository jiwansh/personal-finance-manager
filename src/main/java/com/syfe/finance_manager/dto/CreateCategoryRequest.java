package com.syfe.finance_manager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCategoryRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String type;   // INCOME or EXPENSE
}
