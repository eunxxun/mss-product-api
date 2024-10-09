package com.eunsun.mssproductapi.category.Service;

import com.eunsun.mssproductapi.category.entity.Category;
import com.eunsun.mssproductapi.category.repository.CategoryRepository;
import com.eunsun.mssproductapi.common.exception.ExceptionMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    @Override
    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_CATEGORY));
    }

    @Override
    public Boolean existsByCategoryNm(String categoryNm) {
        return categoryRepository.existsByCategoryNm(categoryNm);
    }
}
