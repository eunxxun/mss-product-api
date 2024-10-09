package com.eunsun.mssproductapi.price.dto;

import java.math.BigDecimal;

public interface BrandPriceRange {
    String getLowestPriceBrand();
    BigDecimal getLowestPrice();
    String getHighestPriceBrand();
    BigDecimal getHighestPrice();
}
