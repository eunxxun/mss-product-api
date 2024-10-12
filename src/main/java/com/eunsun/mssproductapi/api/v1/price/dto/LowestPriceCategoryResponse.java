package com.eunsun.mssproductapi.api.v1.price.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public record LowestPriceCategoryResponse(List<LowestPriceCategory> lowestPriceCategoryList, BigDecimal totalPrice) implements Serializable {

}
