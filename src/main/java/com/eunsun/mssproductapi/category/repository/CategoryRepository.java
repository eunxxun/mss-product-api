package com.eunsun.mssproductapi.category.repository;

import com.eunsun.mssproductapi.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByCategoryNm(String categoryNm);
}
