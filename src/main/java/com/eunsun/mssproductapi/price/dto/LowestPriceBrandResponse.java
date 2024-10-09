package com.eunsun.mssproductapi.price.dto;

import java.math.BigDecimal;
import java.util.List;

public record LowestPriceBrandResponse(String brandNm, List<CategoryPrice> categories, BigDecimal totalPrice) {
}
