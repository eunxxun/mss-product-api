package com.eunsun.mssproductapi.domain.price.service;

import com.eunsun.mssproductapi.api.v1.price.dto.*;
import com.eunsun.mssproductapi.common.exception.ExceptionMessage;
import com.eunsun.mssproductapi.common.exception.LowestPriceBrandNotFoundException;
import com.eunsun.mssproductapi.domain.category.entity.Category;
import com.eunsun.mssproductapi.domain.category.service.CategoryService;
import com.eunsun.mssproductapi.domain.price.repository.ProductNativeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @Mock
    private ProductNativeRepository productRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private CategoryPriceCacheHandler categoryPriceCacheHandler;

    @InjectMocks
    private PriceServiceImpl priceService;

    @Test
    @DisplayName("카테고리 별 최저가인 브랜드와 상품의 가격을 조회하고 총액도 함께 조회하는 테스트")
    void getCategoryLowestPricesTest() {
        // given
        List<LowestPriceCategoryInterface> lowestPriceCategoryInterfaceList = createLowestPriceCategoryList();
        when(productRepository.findCategoryMinPrices()).thenReturn(lowestPriceCategoryInterfaceList);

        // when
        LowestPriceCategoryResponse response = priceService.getCategoryLowestPrices();

        // then
        assertNotNull(response);
        assertEquals(2, response.lowestPriceCategoryList().size());
        assertEquals(new BigDecimal("25000"), response.totalPrice());
        verify(productRepository, times(1)).findCategoryMinPrices();
    }

    @Test
    @DisplayName("단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저가인 브랜드와 총액 조회하는 테스트")
    void getBrandLowestPricesTest() {
        // given
        LowestPriceBrandInterface lowestPriceBrand = createLowestPriceBrand();
        List<CategoryPrice> categoryPrices = List.of(
                new CategoryPrice("상의", new BigDecimal("10000")),
                new CategoryPrice("바지", new BigDecimal("20000"))
        );

        when(productRepository.findLowestPriceBrandWithTotal()).thenReturn(Optional.of(lowestPriceBrand));
        when(productRepository.findCategoryMinPricesByBrand(anyLong())).thenReturn(categoryPrices);

        // when
        LowestPriceBrandResponse response = priceService.getBrandLowestPrices();

        // then
        assertNotNull(response);
        assertEquals("아디다스", response.brandNm());
        assertEquals(2, response.categories().size());
        assertEquals(new BigDecimal("30000"), response.totalPrice());
        verify(productRepository, times(1)).findLowestPriceBrandWithTotal();
        verify(productRepository, times(1)).findCategoryMinPricesByBrand(anyLong());
    }

    @Test
    @DisplayName("단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저가격인 브랜드와 총액 조회 예외 발생 테스트")
    void getBrandLowestPricesNotFoundExceptionTest() {
        // given
        when(productRepository.findLowestPriceBrandWithTotal()).thenReturn(Optional.empty());

        // when & then
        LowestPriceBrandNotFoundException exception = assertThrows(LowestPriceBrandNotFoundException.class, () -> {
            priceService.getBrandLowestPrices();
        });
        assertEquals(ExceptionMessage.NOT_FOUND_LOWEST_BRAND, exception.getMessage());
        verify(productRepository, times(1)).findLowestPriceBrandWithTotal();
    }

    @Test
    @DisplayName("특정 카테고리에서 최저가 브랜드, 최고가 브랜드 조회하고 해당 브랜드 상품의 가격 조회하는 테스트")
    void getCategoryPriceRangeByNameTest() {
        // given
        String categoryNm = "상의";
        Category category = new Category(1L, categoryNm);
        CategoryPriceRangeResponse categoryPriceRangeResponse = new CategoryPriceRangeResponse(categoryNm, List.of(), List.of());

        when(categoryService.findCategoryByName(anyString())).thenReturn(category);
        when(categoryPriceCacheHandler.getCategoryPriceRange(any(Category.class))).thenReturn(categoryPriceRangeResponse);

        // when
        CategoryPriceRangeResponse response = priceService.getCategoryPriceRangeByName(categoryNm);

        // then
        assertNotNull(response);
        assertEquals(categoryNm, response.categoryNm());
        verify(categoryService, times(1)).findCategoryByName(anyString());
        verify(categoryPriceCacheHandler, times(1)).getCategoryPriceRange(any(Category.class));
    }

    @Test
    @DisplayName("특정 카테고리에서 최저가 브랜드, 최고가 브랜드 조회하고 해당 브랜드 상품의 가격 조회 예외 발생 테스트")
    void getCategoryPriceRangeByNameNotFoundExceptionTest() {
        // given
        String categoryNm = "노트북"; //없는 카테고리
        when(categoryService.findCategoryByName(anyString())).thenThrow(new EntityNotFoundException(ExceptionMessage.NOT_FOUND_CATEGORY));

        // when & then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            priceService.getCategoryPriceRangeByName(categoryNm);
        });
        assertEquals(ExceptionMessage.NOT_FOUND_CATEGORY, exception.getMessage());
        verify(categoryService, times(1)).findCategoryByName(anyString());
    }

    private List<LowestPriceCategoryInterface> createLowestPriceCategoryList() {
        return List.of(
                new LowestPriceCategoryInterface() {
                    @Override
                    public String getCategoryNm() {
                        return "상의";
                    }

                    @Override
                    public BigDecimal getPrice() {
                        return new BigDecimal("10000");
                    }

                    @Override
                    public String getBrandNm() {
                        return "아디다스";
                    }
                },
                new LowestPriceCategoryInterface() {
                    @Override
                    public String getCategoryNm() {
                        return "바지";
                    }

                    @Override
                    public BigDecimal getPrice() {
                        return new BigDecimal("15000");
                    }

                    @Override
                    public String getBrandNm() {
                        return "나이키";
                    }
                }
        );
    }

    private LowestPriceBrandInterface createLowestPriceBrand() {
        return new LowestPriceBrandInterface() {
            @Override
            public Long getBrandId() {
                return 1L;
            }

            @Override
            public String getBrandNm() {
                return "아디다스";
            }

            @Override
            public BigDecimal getTotalPrice() {
                return new BigDecimal("30000");
            }
        };
    }


}