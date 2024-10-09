package com.eunsun.mssproductapi.price.dto;

import java.math.BigDecimal;

public interface LowestPriceCategory {
    String getCategoryNm();
    String getBrandNm();
    BigDecimal getPrice();
}
