package com.eunsun.mssproductapi.common.aop;

import com.eunsun.mssproductapi.domain.brand.entity.Brand;
import com.eunsun.mssproductapi.domain.brand.repository.BrandRepository;
import com.eunsun.mssproductapi.domain.brand.service.BrandService;
import com.eunsun.mssproductapi.domain.product.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BrandAspectTest {

    @MockBean
    private BrandRepository brandRepository;

    @MockBean
    private ProductService productService;

    @Autowired
    private BrandService brandService;

    @Test
    @DisplayName("브랜드 삭제 시 상품 소프트 삭제 테스트")
    void deleteBrandWithProductSoftDeleteTest() {
        // given
        Long brandId = 1L;
        Brand existingBrand = new Brand(brandId, "브랜드", false);
        when(brandRepository.findById(anyLong())).thenReturn(Optional.of(existingBrand));

        // when
        brandService.delete(brandId);

        // then
        verify(brandRepository, times(1)).delete(existingBrand);
        verify(productService, times(1)).deleteByBrandId(brandId);
    }
}