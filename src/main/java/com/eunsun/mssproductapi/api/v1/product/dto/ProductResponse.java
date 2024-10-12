package com.eunsun.mssproductapi.api.v1.product.dto;

import java.math.BigDecimal;

public record ProductResponse(Long id,
                              String brandNm,
                              String categoryNm,
                              String productNm,
                              BigDecimal price) {
}
