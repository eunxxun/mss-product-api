package com.eunsun.mssproductapi.price.controller;

import com.eunsun.mssproductapi.price.dto.LowestPriceBrandResponse;
import com.eunsun.mssproductapi.price.dto.LowestPriceCategoryResponse;
import com.eunsun.mssproductapi.price.dto.CategoryPriceRangeResponse;
import com.eunsun.mssproductapi.price.service.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "가격비교")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/prices")
public class PriceController {
    private final PriceService priceService;
    @GetMapping("/category-min")
    @Operation(summary = "카테고리별 최저가 브랜드,상품,총액 조회", description = "카테고리 별로 최저가격인 브랜드와 상품의 가격을 조회하고 총액이 얼마인지 조회합니다.")
    public ResponseEntity<LowestPriceCategoryResponse> getCategoryLowestPrices() {
        LowestPriceCategoryResponse response = priceService.getCategoryLowestPrices();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/brand-min")
    @Operation(summary = "단일 브랜드 전체 카테고리 최저가 상품총액 조회", description = "단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저가격인 브랜드와 총액을 조회합니다.")
    public ResponseEntity<LowestPriceBrandResponse> getBrandLowestPrices() {
        LowestPriceBrandResponse response = priceService.getBrandLowestPrices();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{categoryNm}")
    @Operation(summary = "카테고리명으로 최저, 최고 가격 브랜드와 상품 가격을 조회", description = "특정 카테고리에서 최저가격 브랜드와 최고가격 브랜드를 조회하고 각 브랜드 상품의 가격을 조회합니다.")
    public ResponseEntity<CategoryPriceRangeResponse> getCategoryPriceRange(@PathVariable("categoryNm") String categoryNm) {
        CategoryPriceRangeResponse response = priceService.getCategoryPriceRange(categoryNm);
        return ResponseEntity.ok(response);
    }

    //TODO 상품이 등록,수정,삭제 될 때 동시에 가격을 조회한다면?
    //TODO 상품이 많아질수록 가격비교 api는 느려질것. 어떻게 대비할 것인지?
}
