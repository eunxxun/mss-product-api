package com.eunsun.mssproductapi.domain.product.service;

import com.eunsun.mssproductapi.api.v1.product.dto.ProductRequest;
import com.eunsun.mssproductapi.api.v1.product.dto.ProductResponse;
import com.eunsun.mssproductapi.common.exception.DuplicationException;
import com.eunsun.mssproductapi.common.exception.ExceptionMessage;
import com.eunsun.mssproductapi.domain.brand.entity.Brand;
import com.eunsun.mssproductapi.domain.brand.service.BrandService;
import com.eunsun.mssproductapi.domain.category.entity.Category;
import com.eunsun.mssproductapi.domain.category.service.CategoryService;
import com.eunsun.mssproductapi.domain.product.entity.Product;
import com.eunsun.mssproductapi.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BrandService brandService;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private ProductServiceImpl productService;

    private Brand brand;
    private Category category;
    private Product product;
    private Long productId;

    @BeforeEach
    void setup() {
        productId = 1L;
        brand = new Brand(1L, "브랜드", false);
        category = new Category(1L, "카테고리");
        product = new Product(1L, brand, category, "신규상품", new BigDecimal("10000"), false);
    }

    @Test
    @DisplayName("상품 생성 테스트")
    void createProductTest() {
        // given
        ProductRequest productRequest = new ProductRequest(1L, 1L, "신규상품", new BigDecimal("10000"));

        when(brandService.findById(anyLong())).thenReturn(brand);
        when(categoryService.findCategoryById(anyLong())).thenReturn(category);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // when
        ProductResponse productResponse = productService.create(productRequest);

        // then
        assertNotNull(productResponse);
        assertEquals("신규상품", productResponse.ProductNm());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("상품 생성 시 중복된 상품 예외 발생 테스트")
    void createProductDuplicateExceptionTest() {
        // given
        ProductRequest productRequest = new ProductRequest(1L, 1L, "신규상품", new BigDecimal("10000"));

        when(brandService.findById(anyLong())).thenReturn(brand);
        when(categoryService.findCategoryById(anyLong())).thenReturn(category);
        when(productRepository.existsByBrandAndCategoryAndProductNmAndPrice(brand, category, productRequest.productNm(), productRequest.price())).thenReturn(true);

        // when & then
        DuplicationException exception = assertThrows(DuplicationException.class, () -> productService.create(productRequest));
        assertEquals(ExceptionMessage.EXISTS_PRODUCT, exception.getMessage());
        verify(productRepository, times(1)).existsByBrandAndCategoryAndProductNmAndPrice(brand, category, productRequest.productNm(), productRequest.price());
    }

    @Test
    @DisplayName("상품 정보 수정 테스트")
    void updateProductTest() {
        // given
        ProductRequest productRequest = new ProductRequest(2L, 2L, "수정상품", new BigDecimal("15000"));
        Brand brand = new Brand(2L, "수정브랜드", false);
        Category category = new Category(2L, "수정카테고리");

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(brandService.findById(anyLong())).thenReturn(brand);
        when(categoryService.findCategoryById(anyLong())).thenReturn(category);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // when
        ProductResponse productResponse = productService.update(productId, productRequest);

        // then
        assertNotNull(productResponse);
        assertEquals("수정상품", productResponse.ProductNm());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    @DisplayName("상품 수정 시 중복된 상품 예외 발생 테스트")
    void updateProductDuplicateExceptionTest() {
        // given
        ProductRequest productRequest = new ProductRequest(1L, 1L, "신규상품", new BigDecimal("10000"));

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(brandService.findById(anyLong())).thenReturn(brand);
        when(categoryService.findCategoryById(anyLong())).thenReturn(category);
        when(productRepository.existsByBrandAndCategoryAndProductNmAndPrice(brand, category, productRequest.productNm(), productRequest.price())).thenReturn(true);

        // when & then
        DuplicationException exception = assertThrows(DuplicationException.class, () -> productService.update(productId, productRequest));
        assertEquals(ExceptionMessage.EXISTS_PRODUCT, exception.getMessage());
        verify(productRepository, times(1)).existsByBrandAndCategoryAndProductNmAndPrice(brand, category, productRequest.productNm(), productRequest.price());
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteProductTest() {
        // given
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        // when
        productService.delete(productId);

        // then
        verify(productRepository, times(1)).delete(product);
    }

}