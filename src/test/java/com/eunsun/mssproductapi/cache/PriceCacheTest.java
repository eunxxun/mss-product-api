package com.eunsun.mssproductapi.cache;

import com.eunsun.mssproductapi.api.v1.price.dto.LowestPriceCategoryResponse;
import com.eunsun.mssproductapi.api.v1.product.dto.ProductRequest;
import com.eunsun.mssproductapi.domain.price.service.PriceService;
import com.eunsun.mssproductapi.domain.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PriceCacheTest {

    @Autowired
    private PriceService priceService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() {
        Cache cache = cacheManager.getCache("categoryLowestPrices");
        if (cache != null) {
            cache.clear();
        }
    }

    @Test
    @DisplayName("categoryLowestPrices 캐시확인")
    @Deprecated
    void getCategoryLowestPricesCacheableTest() {
        // 최초 데이터 조회(캐시x)
        LowestPriceCategoryResponse firstResponse = priceService.getCategoryLowestPrices();
        // 이후 동일한 요청(캐시o)
        LowestPriceCategoryResponse cachedResponse = priceService.getCategoryLowestPrices();

        // 캐시에서 반환된 데이터와 첫 번째 호출 시 반환된 데이터가 동일한지 확인
        assertThat(cachedResponse).isEqualTo(firstResponse);

        // 캐시 정상 동작 확인
        Cache cache = cacheManager.getCache("categoryLowestPrices");
        assertThat(cache).isNotNull();
        Cache.ValueWrapper wrapper = cache.get("ctgLowestKey");
        assertThat(wrapper).isNotNull();
    }

    @Test
    @DisplayName("categoryLowestPrices 캐시무효화 확인")
    @Deprecated
    void getCategoryLowestPricesCacheEvictTest() {
        // 캐시 저장
        priceService.getCategoryLowestPrices();

        // 캐시 무효화 전에 캐시가 존재하는지 확인
        Cache cache = cacheManager.getCache("categoryLowestPrices");
        assertThat(cache.get("ctgLowestKey")).isNotNull();

        // 상품 정보 업데이트 -> 캐시 무효화
        ProductRequest request = new ProductRequest(1L, 1L, "Updated Product", BigDecimal.valueOf(200));
        productService.update(1L, request);

        // 캐시가 무효화되었는지 확인
        assertThat(cache.get("ctgLowestKey")).isNull();
    }
}
