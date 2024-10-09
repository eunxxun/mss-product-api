package com.eunsun.mssproductapi.price.dto;

import java.util.List;

public record CategoryPriceRangeResponse(String categoryNm, List<BrandPrice> lowestPrices, List<BrandPrice> highestPrices) {

}
