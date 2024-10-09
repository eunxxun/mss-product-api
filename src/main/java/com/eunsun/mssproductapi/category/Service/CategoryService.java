package com.eunsun.mssproductapi.category.Service;

import com.eunsun.mssproductapi.category.entity.Category;

public interface CategoryService {
    Category findCategoryById(Long id);

    Boolean existsByCategoryNm(String categoryNm);
}
