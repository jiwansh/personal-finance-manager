package com.syfe.finance_manager.controller;

import com.syfe.finance_manager.dto.CategoryResponse;
import com.syfe.finance_manager.dto.CreateCategoryRequest;
import com.syfe.finance_manager.service.CategoryService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponse> getCategories(HttpSession session){

        Long userId = (Long) session.getAttribute("userId");
        return categoryService.getCategories(userId);
    }

    @PostMapping
    public CategoryResponse createCategory(@RequestBody CreateCategoryRequest request,
                                           HttpSession session){

        Long userId = (Long) session.getAttribute("userId");

        return categoryService.createCategory(request, userId);
    }
    @DeleteMapping("/{name}")
    public String deleteCategory(@PathVariable String name, HttpSession session){

        Long userId = (Long) session.getAttribute("userId");

        return categoryService.deleteCategory(name, userId);
    }
}
