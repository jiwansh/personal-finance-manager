package com.syfe.finance_manager.service;

import com.syfe.finance_manager.dto.CreateCategoryRequest;
import com.syfe.finance_manager.entity.Category;
import com.syfe.finance_manager.exception.ConflictException;
import com.syfe.finance_manager.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryService categoryService;

    public CategoryServiceTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCategory_success(){

        CreateCategoryRequest req = new CreateCategoryRequest();
        req.setName("Freelance");
        req.setType("INCOME");

        when(categoryRepository.existsByNameAndUserId("Freelance",1L)).thenReturn(false);

        Category result = Category.builder()
                .name("Freelance")
                .type(Category.Type.INCOME)
                .isCustom(true)
                .userId(1L)
                .build();

        when(categoryRepository.save(any())).thenReturn(result);

        var response = categoryService.createCategory(req,1L);

        assertEquals("Freelance", response.getName());
    }

    @Test
    void duplicate_category(){

        CreateCategoryRequest req = new CreateCategoryRequest();
        req.setName("Food");

        when(categoryRepository.existsByNameAndUserId("Food",1L)).thenReturn(true);

        assertThrows(ConflictException.class,
                () -> categoryService.createCategory(req,1L));
    }
}
