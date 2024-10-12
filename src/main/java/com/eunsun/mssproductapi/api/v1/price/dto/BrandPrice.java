package com.eunsun.mssproductapi.api.v1.price.dto;

import java.math.BigDecimal;

public record BrandPrice (String brandNm, BigDecimal price) {
}
