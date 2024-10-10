package com.eunsun.mssproductapi.brand.repository;

import com.eunsun.mssproductapi.brand.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    boolean existsByBrandNm(String brandNm);
}
