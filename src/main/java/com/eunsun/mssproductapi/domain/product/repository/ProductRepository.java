package com.eunsun.mssproductapi.domain.product.repository;

import com.eunsun.mssproductapi.domain.brand.entity.Brand;
import com.eunsun.mssproductapi.domain.category.entity.Category;
import com.eunsun.mssproductapi.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Query("UPDATE Product p SET p.delYn = true WHERE p.brand.id = :brandId")
    void softDeleteProductsByBrand(@Param("brandId") Long brandId);

    boolean existsByBrandAndCategoryAndProductNmAndPrice(Brand brand, Category category, String productNm, BigDecimal price);
}
