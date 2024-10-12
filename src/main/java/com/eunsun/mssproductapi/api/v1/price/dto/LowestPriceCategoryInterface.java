package com.eunsun.mssproductapi.api.v1.price.dto;

import java.math.BigDecimal;

public interface LowestPriceCategoryInterface {
    String getCategoryNm();
    String getBrandNm();
    BigDecimal getPrice();
}
