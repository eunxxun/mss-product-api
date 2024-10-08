package com.eunsun.mssproductapi.category.repository;

import com.eunsun.mssproductapi.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
