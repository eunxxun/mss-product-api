package com.eunsun.mssproductapi.domain.price.mapper;

import com.eunsun.mssproductapi.api.v1.price.dto.LowestPriceCategory;
import com.eunsun.mssproductapi.api.v1.price.dto.LowestPriceCategoryInterface;

import java.util.List;
import java.util.stream.Collectors;

public class PriceMapper {

    // 인터페이스 기반 객체를 직렬화하기 위해 구체적인 클래스로 변환
    public static List<LowestPriceCategory> toLowestPriceCategory(List<LowestPriceCategoryInterface> lowestPriceCategoryInterfaceList) {
        return lowestPriceCategoryInterfaceList.stream()
                .map(item -> new LowestPriceCategory(item.getCategoryNm(), item.getBrandNm(), item.getPrice()))
                .collect(Collectors.toList());
    }
}
