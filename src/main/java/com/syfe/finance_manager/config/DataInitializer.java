package com.syfe.finance_manager.config;

import com.syfe.finance_manager.entity.Category;
import com.syfe.finance_manager.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {

        if(categoryRepository.count() == 0){

            // INCOME
            categoryRepository.save(Category.builder()
                    .name("Salary")
                    .type(Category.Type.INCOME)
                    .isCustom(false)
                    .build());

            // EXPENSE
            String[] expenses =
                    {"Food","Rent","Transportation","Entertainment","Healthcare","Utilities"};

            for(String exp : expenses){
                categoryRepository.save(Category.builder()
                        .name(exp)
                        .type(Category.Type.EXPENSE)
                        .isCustom(false)
                        .build());
            }
        }
    }
}
