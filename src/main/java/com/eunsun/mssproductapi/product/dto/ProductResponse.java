package com.eunsun.mssproductapi.product.dto;

import java.math.BigDecimal;

public record ProductResponse(Long id,
                              String brandNm,
                              String CategoryNm,
                              String ProductNm,
                              BigDecimal price) {
}
