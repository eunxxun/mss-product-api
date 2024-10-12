package com.eunsun.mssproductapi.api.v1.price.dto;

import java.math.BigDecimal;

public interface LowestPriceBrandInterface {
    Long getBrandId();
    String getBrandNm();
    BigDecimal getTotalPrice();
}
