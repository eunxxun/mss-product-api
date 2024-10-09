package com.eunsun.mssproductapi.price.service;

import com.eunsun.mssproductapi.price.dto.LowestPriceBrandResponse;
import com.eunsun.mssproductapi.price.dto.LowestPriceCategoryResponse;
import com.eunsun.mssproductapi.price.dto.CategoryPriceRangeResponse;

public interface PriceService {
    LowestPriceCategoryResponse getCategoryLowestPrices();

    LowestPriceBrandResponse getBrandLowestPrices();

    CategoryPriceRangeResponse getCategoryPriceRange(String categoryNm);
}
