package com.eunsun.mssproductapi.price.dto;

import java.math.BigDecimal;

public interface LowestPriceBrand {
    Long getBrandId();
    String getBrandNm();
    BigDecimal getTotalPrice();
}
