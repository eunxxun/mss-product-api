package com.eunsun.mssproductapi.price.dto;

import java.math.BigDecimal;
import java.util.List;

public record LowestPriceCategoryResponse(List<LowestPriceCategory> lowestPriceCategoryList, BigDecimal totalPrice) {
}
