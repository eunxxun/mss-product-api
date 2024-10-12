package com.eunsun.mssproductapi.domain.price.service;

import com.eunsun.mssproductapi.api.v1.price.dto.LowestPriceBrandResponse;
import com.eunsun.mssproductapi.api.v1.price.dto.LowestPriceCategoryResponse;
import com.eunsun.mssproductapi.api.v1.price.dto.CategoryPriceRangeResponse;

public interface PriceService {
    LowestPriceCategoryResponse getCategoryLowestPrices();

    LowestPriceBrandResponse getBrandLowestPrices();

    CategoryPriceRangeResponse getCategoryPriceRangeByName(String categoryNm);
}
