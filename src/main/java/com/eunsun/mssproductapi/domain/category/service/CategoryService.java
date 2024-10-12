package com.eunsun.mssproductapi.domain.category.service;

import com.eunsun.mssproductapi.domain.category.entity.Category;

public interface CategoryService {
    Category findCategoryById(Long id);

    Category findCategoryByName(String categoryNm);
}
