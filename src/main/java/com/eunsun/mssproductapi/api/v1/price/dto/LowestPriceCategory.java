package com.eunsun.mssproductapi.api.v1.price.dto;

import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

public record LowestPriceCategory (String categoryNm, String brandNm, BigDecimal price) implements Serializable {
}
