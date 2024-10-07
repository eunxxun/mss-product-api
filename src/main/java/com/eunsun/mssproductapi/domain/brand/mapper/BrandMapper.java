package com.eunsun.mssproductapi.domain.brand.mapper;

import com.eunsun.mssproductapi.domain.brand.dto.BrandRequest;
import com.eunsun.mssproductapi.domain.brand.entity.Brand;

public class BrandMapper {
    public static Brand toEntity(BrandRequest brandRequest) {
        return Brand.builder().brandNm(brandRequest.brandNm()).build();
    }
}
