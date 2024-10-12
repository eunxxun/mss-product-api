package com.eunsun.mssproductapi.domain.price.service;

import com.eunsun.mssproductapi.api.v1.price.dto.BrandPrice;
import com.eunsun.mssproductapi.api.v1.price.dto.BrandPriceRange;
import com.eunsun.mssproductapi.api.v1.price.dto.CategoryPriceRangeResponse;
import com.eunsun.mssproductapi.domain.category.entity.Category;
import com.eunsun.mssproductapi.domain.price.repository.ProductNativeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryPriceCacheHandler {
    private final ProductNativeRepository productRepository;

    @Cacheable(value = "categoryRangePrices", key = "#category.id")
    public CategoryPriceRangeResponse getCategoryPriceRange(Category category) {
        BrandPriceRange brandPriceRange = productRepository.findBrandPriceRangeByCategoryId(category.getId());
        List<BrandPrice> lowestPrices = List.of(new BrandPrice(brandPriceRange.getLowestPriceBrand(), brandPriceRange.getLowestPrice()));
        List<BrandPrice> highestPrices = List.of(new BrandPrice(brandPriceRange.getHighestPriceBrand(), brandPriceRange.getHighestPrice()));

        return new CategoryPriceRangeResponse(category.getCategoryNm(), lowestPrices, highestPrices);
    }
}
