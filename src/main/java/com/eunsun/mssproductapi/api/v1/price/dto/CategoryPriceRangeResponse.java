package com.eunsun.mssproductapi.api.v1.price.dto;

import java.io.Serializable;
import java.util.List;

public record CategoryPriceRangeResponse(String categoryNm, List<BrandPrice> lowestPrices, List<BrandPrice> highestPrices) implements Serializable {

}
