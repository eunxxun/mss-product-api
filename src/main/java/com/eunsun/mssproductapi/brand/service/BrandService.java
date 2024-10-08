package com.eunsun.mssproductapi.brand.service;

import com.eunsun.mssproductapi.brand.dto.BrandRequest;
import com.eunsun.mssproductapi.brand.dto.BrandResponse;
import com.eunsun.mssproductapi.brand.entity.Brand;

public interface BrandService {
    BrandResponse create(BrandRequest brandRequest);
    BrandResponse update(Long id, BrandRequest brandRequest);
    void delete(Long id);
    Brand findById(Long id);
}
