package com.eunsun.mssproductapi.domain.category.service;

import com.eunsun.mssproductapi.domain.category.entity.Category;
import com.eunsun.mssproductapi.domain.category.repository.CategoryRepository;
import com.eunsun.mssproductapi.common.exception.ExceptionMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_CATEGORY));
    }

    @Override
    public Category findCategoryByName(String categoryNm) {
        return categoryRepository.findCategoryByCategoryNm(categoryNm)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_CATEGORY));
    }
}
