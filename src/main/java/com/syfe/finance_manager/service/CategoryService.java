package com.syfe.finance_manager.service;

import com.syfe.finance_manager.dto.CategoryResponse;
import com.syfe.finance_manager.dto.CreateCategoryRequest;
import com.syfe.finance_manager.entity.Category;
import com.syfe.finance_manager.repository.CategoryRepository;

import com.syfe.finance_manager.exception.ConflictException;
import com.syfe.finance_manager.exception.NotFoundException;
import com.syfe.finance_manager.exception.ForbiddenException;
import com.syfe.finance_manager.exception.BadRequestException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // GET ALL
    public List<CategoryResponse> getCategories(Long userId){

        List<Category> categories = categoryRepository.findByUserIdOrIsCustomFalse(userId);

        return categories.stream()
                .map(c -> new CategoryResponse(c.getName(), c.getType().name(), c.isCustom()))
                .toList();
    }

    // CREATE CATEGORY
    public CategoryResponse createCategory(CreateCategoryRequest request, Long userId){

        // duplicate category
        if(categoryRepository.existsByNameAndUserId(request.getName(), userId)){
            throw new ConflictException("Category already exists");
        }

        // invalid type safety
        Category.Type type;
        try {
            type = Category.Type.valueOf(request.getType().toUpperCase());
        } catch (Exception e){
            throw new BadRequestException("Invalid category type. Use INCOME or EXPENSE");
        }

        Category category = Category.builder()
                .name(request.getName())
                .type(type)
                .isCustom(true)
                .userId(userId)
                .build();

        categoryRepository.save(category);

        return new CategoryResponse(category.getName(), category.getType().name(), true);
    }

    // DELETE CATEGORY
    public String deleteCategory(String name, Long userId){

        Category category = categoryRepository
                .findByNameAndUserId(name, userId)
                .orElseThrow(() -> new NotFoundException("Category not found"));

        if(!category.isCustom()){
            throw new ForbiddenException("Default category cannot be deleted");
        }

        categoryRepository.delete(category);

        return "Category deleted successfully";
    }
}
