package com.syfe.finance_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryResponse {

    private String name;
    private String type;
    private boolean isCustom;
}
