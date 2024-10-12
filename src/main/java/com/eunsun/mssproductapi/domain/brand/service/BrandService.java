package com.eunsun.mssproductapi.domain.brand.service;

import com.eunsun.mssproductapi.api.v1.brand.dto.BrandRequest;
import com.eunsun.mssproductapi.api.v1.brand.dto.BrandResponse;
import com.eunsun.mssproductapi.domain.brand.entity.Brand;

public interface BrandService {
    BrandResponse create(BrandRequest brandRequest);
    BrandResponse update(Long id, BrandRequest brandRequest);
    void delete(Long id);
    Brand findById(Long id);
}
