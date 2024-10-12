package com.eunsun.mssproductapi.domain.category.repository;

import com.eunsun.mssproductapi.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findCategoryByCategoryNm(String categoryNm);
}
