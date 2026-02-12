package com.syfe.finance_manager.repository;

import com.syfe.finance_manager.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUserIdOrIsCustomFalse(Long userId);

    boolean existsByNameAndUserId(String name, Long userId);

    Optional<Category> findByNameAndUserId(String name, Long userId);
}
