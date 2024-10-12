package com.eunsun.mssproductapi.api.v1.price.dto;

import java.math.BigDecimal;

public record CategoryPrice(String categoryNm, BigDecimal price) {
}
