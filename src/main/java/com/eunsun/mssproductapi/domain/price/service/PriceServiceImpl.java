package com.eunsun.mssproductapi.domain.price.service;

import com.eunsun.mssproductapi.api.v1.price.dto.*;
import com.eunsun.mssproductapi.common.exception.ExceptionMessage;
import com.eunsun.mssproductapi.common.exception.LowestPriceBrandNotFoundException;
import com.eunsun.mssproductapi.domain.category.entity.Category;
import com.eunsun.mssproductapi.domain.category.service.CategoryService;
import com.eunsun.mssproductapi.domain.price.mapper.PriceMapper;
import com.eunsun.mssproductapi.domain.price.repository.ProductNativeRepository;
import org.springframework.cache.annotation.Cacheable;
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
    private final CategoryPriceCacheHandler categoryPriceCacheHandler;

    /**
     * 카테고리 별로 최저가격인 브랜드와 상품의 가격을 조회하고 총액이 얼마인지 조회
     * @return CategoryMinPriceResponse
     */
    @Override
    @Cacheable(value = "categoryLowestPrices", key = "'ctgLowestKey'")
    public LowestPriceCategoryResponse getCategoryLowestPrices() {
        List<LowestPriceCategoryInterface> lowestPriceCategoryInterfaceList = productRepository.findCategoryMinPrices();
        BigDecimal totalPrice = lowestPriceCategoryInterfaceList.stream()
                .map(LowestPriceCategoryInterface::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new LowestPriceCategoryResponse(PriceMapper.toLowestPriceCategory(lowestPriceCategoryInterfaceList), totalPrice);
    }

    /**
     * 단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저가격인 브랜드와 총액 조회
     * @return BrandMinPriceResponse
     */
    @Override
    @Cacheable(value = "brandLowestPrices", key = "'brdLowestKey'")
    public LowestPriceBrandResponse getBrandLowestPrices() {
        LowestPriceBrandInterface lowestPriceBrandInterface = productRepository.findLowestPriceBrandWithTotal()
                .orElseThrow(() -> new LowestPriceBrandNotFoundException(ExceptionMessage.NOT_FOUND_LOWEST_BRAND));

        List<CategoryPrice> categoryPrices = productRepository.findCategoryMinPricesByBrand(lowestPriceBrandInterface.getBrandId());
        return new LowestPriceBrandResponse(lowestPriceBrandInterface.getBrandNm(), categoryPrices, lowestPriceBrandInterface.getTotalPrice());
    }

    /**
     * 특정 카테고리에서 최저가격 브랜드와 최고가격 브랜드를 조회하고 각 브랜드 상품의 가격 조회
     * @param categoryNm 카테고리명
     * @return CategoryPriceRangeResponse
     */
    @Override
    public CategoryPriceRangeResponse getCategoryPriceRangeByName(String categoryNm) {
        Category category = categoryService.findCategoryByName(categoryNm);
        return categoryPriceCacheHandler.getCategoryPriceRange(category);
    }

}
