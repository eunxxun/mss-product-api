package com.eunsun.mssproductapi.brand.mapper;

import com.eunsun.mssproductapi.brand.dto.BrandRequest;
import com.eunsun.mssproductapi.brand.dto.BrandResponse;
import com.eunsun.mssproductapi.brand.entity.Brand;

public class BrandMapper {
    public static Brand toEntity(BrandRequest brandRequest) {
        return Brand.builder().brandNm(brandRequest.brandNm()).build();
    }

    public static BrandResponse toResponse(Brand brand) {
        return new BrandResponse(brand.getId(), brand.getBrandNm());
    }
}
