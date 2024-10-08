package com.eunsun.mssproductapi.brand.repository;

import com.eunsun.mssproductapi.brand.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    boolean existsByBrandNm(String brandNm);
}
