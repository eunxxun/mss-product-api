package com.eunsun.mssproductapi.product.dto;

import java.math.BigDecimal;

public record ProductRequest(Long brandId,
                             Long categoryId,
                             String productNm,
                             BigDecimal price) {
}
