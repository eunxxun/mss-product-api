package com.eunsun.mssproductapi.price.service;

import com.eunsun.mssproductapi.category.Service.CategoryService;
import com.eunsun.mssproductapi.common.exception.ExceptionMessage;
import com.eunsun.mssproductapi.common.exception.LowestPriceBrandNotFoundException;
import com.eunsun.mssproductapi.price.dto.*;
import com.eunsun.mssproductapi.price.repository.ProductNativeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PriceServiceImpl implements PriceService{
    private final ProductNativeRepository productRepository;
    private final CategoryService categoryService;

    /**
     * 카테고리 별로 최저가격인 브랜드와 상품의 가격을 조회하고 총액이 얼마인지 조회
     * @return CategoryMinPriceResponse
     */
    @Override
    public LowestPriceCategoryResponse getCategoryLowestPrices() {
        List<LowestPriceCategory> lowestPriceCategoryList = productRepository.findCategoryMinPrices();
        BigDecimal totalPrice = lowestPriceCategoryList.stream()
                .map(LowestPriceCategory::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new LowestPriceCategoryResponse(lowestPriceCategoryList, totalPrice);
    }

    /**
     * 단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저가격인 브랜드와 총액 조회
     * @return BrandMinPriceResponse
     */
    @Override
    public LowestPriceBrandResponse getBrandLowestPrices() {
        LowestPriceBrand lowestPriceBrand = productRepository.findLowestPriceBrandWithTotal();
        if (lowestPriceBrand == null) {
            throw new LowestPriceBrandNotFoundException(ExceptionMessage.NOT_FOUND_LOWEST_BRAND);
        }
        List<CategoryPrice> categoryPrices = productRepository.findCategoryMinPricesByBrand(lowestPriceBrand.getBrandId());
        return new LowestPriceBrandResponse(lowestPriceBrand.getBrandNm(), categoryPrices, lowestPriceBrand.getTotalPrice());
    }

    /**
     * 특정 카테고리에서 최저가격 브랜드와 최고가격 브랜드를 조회하고 각 브랜드 상품의 가격 조회
     * @param categoryNm 카테고리명
     * @return CategoryPriceRangeResponse
     */
    @Override
    public CategoryPriceRangeResponse getCategoryPriceRange(String categoryNm) {
        if (!categoryService.existsByCategoryNm(categoryNm)) {
            throw new EntityNotFoundException(ExceptionMessage.NOT_FOUND_CATEGORY);
        }
        BrandPriceRange brandPriceRange = productRepository.findBrandPriceRangeByCategoryNm(categoryNm);
        List<BrandPrice> lowestPrices = List.of(new BrandPrice(brandPriceRange.getLowestPriceBrand(), brandPriceRange.getLowestPrice()));
        List<BrandPrice> highestPrices = List.of(new BrandPrice(brandPriceRange.getHighestPriceBrand(), brandPriceRange.getHighestPrice()));

        return new CategoryPriceRangeResponse(categoryNm, lowestPrices, highestPrices);
    }

}
