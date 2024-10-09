package com.eunsun.mssproductapi.price.dto;

import java.math.BigDecimal;

public record CategoryPrice(String categoryNm, BigDecimal price) {
}
