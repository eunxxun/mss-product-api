package com.eunsun.mssproductapi.api.v1.price.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public record LowestPriceBrandResponse(String brandNm, List<CategoryPrice> categories, BigDecimal totalPrice) implements Serializable {
}
