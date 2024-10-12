package com.eunsun.mssproductapi.domain.price.repository;

import com.eunsun.mssproductapi.api.v1.price.dto.BrandPriceRange;
import com.eunsun.mssproductapi.api.v1.price.dto.CategoryPrice;
import com.eunsun.mssproductapi.api.v1.price.dto.LowestPriceBrand;
import com.eunsun.mssproductapi.api.v1.price.dto.LowestPriceCategoryInterface;
import com.eunsun.mssproductapi.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductNativeRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT category_nm as categoryNm, brand_nm as brandNm, price FROM " +
            "(SELECT c.category_nm, b.brand_nm, p.price, " +
            "ROW_NUMBER() OVER (PARTITION BY p.category_id ORDER BY p.price ASC) as rn " +
            "FROM Product p " +
            "JOIN Brand b ON b.id = p.brand_id " +
            "JOIN Category c ON c.id = p.category_id " +
            "WHERE p.del_yn = false) subquery " +
            "WHERE subquery.rn = 1",
            nativeQuery = true)
    List<LowestPriceCategoryInterface> findCategoryMinPrices();

    @Query(value = "SELECT brand_id as brandId, brand_nm as brandNm, SUM(price) AS totalPrice " +
            "FROM ( " +
            "    SELECT b.id as brand_id, b.brand_nm, c.id, MIN(p.price) AS price " +
            "    FROM Product p " +
            "    JOIN Brand b ON p.brand_id = b.id " +
            "    JOIN Category c ON p.category_id = c.id " +
            "    WHERE p.del_yn = false " +
            "    GROUP BY b.id, b.brand_nm, c.id " +
            ") AS category_min_prices " +
            "GROUP BY brandId " +
            "ORDER BY totalPrice ASC " +
            "LIMIT 1",
            nativeQuery = true)
    Optional<LowestPriceBrand> findLowestPriceBrandWithTotal();

    @Query("SELECT new com.eunsun.mssproductapi.api.v1.price.dto.CategoryPrice(c.categoryNm, MIN(p.price)) " +
            "FROM Product p " +
            "JOIN p.category c " +
            "JOIN p.brand b " +
            "WHERE b.id = :brandId " +
            "AND p.delYn = false " +
            "GROUP BY c.categoryNm")
    List<CategoryPrice> findCategoryMinPricesByBrand(@Param("brandId") Long brandId);

    @Query(value = "SELECT " +
            "b_min.brand_nm AS lowestPriceBrand, " +
            "p_min.price AS lowestPrice, " +
            "b_max.brand_nm AS highestPriceBrand, " +
            "p_max.price AS highestPrice " +
            "FROM product p_min " +
            "JOIN brand b_min ON p_min.brand_id = b_min.id " +
            "JOIN product p_max ON p_min.category_id = p_max.category_id " +
            "JOIN brand b_max ON p_max.brand_id = b_max.id " +
            "JOIN category c ON p_min.category_id = c.id " +
            "WHERE c.id = :categoryId " +
            "AND p_min.price = (SELECT MIN(p2.price) " +
            "                   FROM product p2 " +
            "                   JOIN category c2 ON p2.category_id = c2.id " +
            "                   WHERE c2.id = :categoryId AND p2.del_yn = false) " +
            "AND p_max.price = (SELECT MAX(p3.price) " +
            "                   FROM product p3 " +
            "                   JOIN category c3 ON p3.category_id = c3.id " +
            "                   WHERE c3.id = :categoryId AND p3.del_yn = false) " +
            "AND p_min.del_yn = false " +
            "AND p_max.del_yn = false",
            nativeQuery = true)
    BrandPriceRange findBrandPriceRangeByCategoryId(@Param("categoryId") Long categoryId);
}
