package com.eunsun.mssproductapi.domain.brand.mapper;

import com.eunsun.mssproductapi.api.v1.brand.dto.BrandRequest;
import com.eunsun.mssproductapi.api.v1.brand.dto.BrandResponse;
import com.eunsun.mssproductapi.domain.brand.entity.Brand;

public class BrandMapper {
    public static Brand toEntity(BrandRequest brandRequest) {
        return Brand.builder().brandNm(brandRequest.brandNm()).build();
    }

    public static BrandResponse toResponse(Brand brand) {
        return new BrandResponse(brand.getId(), brand.getBrandNm());
    }
}
