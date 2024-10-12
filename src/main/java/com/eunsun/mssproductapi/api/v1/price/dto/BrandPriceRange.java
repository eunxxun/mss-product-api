package com.eunsun.mssproductapi.api.v1.price.dto;

import java.math.BigDecimal;

public interface BrandPriceRange {
    String getLowestPriceBrand();
    BigDecimal getLowestPrice();
    String getHighestPriceBrand();
    BigDecimal getHighestPrice();
}
