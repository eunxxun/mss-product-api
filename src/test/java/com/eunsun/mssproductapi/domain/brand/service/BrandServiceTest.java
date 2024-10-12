package com.eunsun.mssproductapi.domain.brand.service;

import com.eunsun.mssproductapi.api.v1.brand.dto.BrandRequest;
import com.eunsun.mssproductapi.api.v1.brand.dto.BrandResponse;
import com.eunsun.mssproductapi.common.exception.DuplicationException;
import com.eunsun.mssproductapi.common.exception.ErrorMessages;
import com.eunsun.mssproductapi.domain.brand.entity.Brand;
import com.eunsun.mssproductapi.domain.brand.mapper.BrandMapper;
import com.eunsun.mssproductapi.domain.brand.repository.BrandRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandServiceImpl brandService;

    private Long brandId;

    @BeforeEach
    void setup() {
        brandId = 1L;
    }

    @Test
    @DisplayName("브랜드 생성 테스트")
    void createBrandTest() {
        // given
        BrandRequest brandRequest = new BrandRequest("신규브랜드");
        Brand brand = BrandMapper.toEntity(brandRequest);
        when(brandRepository.save(any(Brand.class))).thenReturn(brand);

        // when
        BrandResponse brandResponse = brandService.create(brandRequest);

        // then
        assertNotNull(brandResponse);
        assertEquals("신규브랜드", brandResponse.brandNm());
        verify(brandRepository, times(1)).save(any(Brand.class));
    }

    @Test
    @DisplayName("브랜드 생성 시 중복된 이름 예외 발생 테스트")
    void createBrandDuplicateNameExceptionTest() {
        // given
        BrandRequest brandRequest = new BrandRequest("중복브랜드");
        when(brandRepository.existsByBrandNm(anyString())).thenReturn(true);

        // when & then
        DuplicationException exception = assertThrows(DuplicationException.class, () -> brandService.create(brandRequest));
        assertEquals(ErrorMessages.EXISTS_BRAND_NM + "중복브랜드", exception.getMessage());
        verify(brandRepository, times(1)).existsByBrandNm(anyString());
    }

    @Test
    @DisplayName("브랜드명 수정 테스트")
    void updateBrandTest() {
        // given
        BrandRequest brandRequest = new BrandRequest("수정브랜드");
        Brand existingBrand = new Brand(brandId, "기존브랜드", false);
        when(brandRepository.findById(anyLong())).thenReturn(java.util.Optional.of(existingBrand));

        // when
        BrandResponse brandResponse = brandService.update(brandId, brandRequest);

        // then
        assertNotNull(brandResponse);
        assertEquals("수정브랜드", brandResponse.brandNm());
        verify(brandRepository, times(1)).save(existingBrand);
    }

    @Test
    @DisplayName("브랜드 수정 시 중복된 이름 예외 발생 테스트")
    void updateBrandDuplicateNameExceptionTest() {
        // given
        Brand existingBrand = new Brand(brandId, "기존브랜드", false);
        BrandRequest brandRequest = new BrandRequest("중복브랜드");

        when(brandRepository.findById(anyLong())).thenReturn(Optional.of(existingBrand));
        when(brandRepository.existsByBrandNm(anyString())).thenReturn(true);

        // when & then
        DuplicationException exception = assertThrows(DuplicationException.class, () -> brandService.update(brandId, brandRequest));
        assertEquals(ErrorMessages.EXISTS_BRAND_NM + "중복브랜드", exception.getMessage());
        verify(brandRepository, times(1)).existsByBrandNm(anyString());
    }

    @Test
    @DisplayName("브랜드 삭제 테스트")
    void deleteBrandTest() {
        // given
        Brand existingBrand = new Brand(brandId, "기존브랜드", true);
        when(brandRepository.findById(anyLong())).thenReturn(java.util.Optional.of(existingBrand));

        // when
        brandService.delete(brandId);

        // then
        verify(brandRepository, times(1)).delete(existingBrand);
        assertTrue(existingBrand.getDelYn());
    }

    @Test
    @DisplayName("브랜드 조회 테스트")
    void findByIdTest() {
        // given
        Brand brand = new Brand(brandId, "기존브랜드", false);
        when(brandRepository.findById(anyLong())).thenReturn(java.util.Optional.of(brand));

        // when
        Brand foundBrand = brandService.findById(brandId);

        // then
        assertNotNull(foundBrand);
        assertEquals("기존브랜드", foundBrand.getBrandNm());
        verify(brandRepository, times(1)).findById(brandId);
    }

    @Test
    @DisplayName("브랜드 조회 예외발생 테스트")
    void findByIdExceptionTest() {
        // given
        when(brandRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());

        // when & then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> brandService.findById(brandId));
        assertEquals(ErrorMessages.NOT_FOUND_BRAND, exception.getMessage());
        verify(brandRepository, times(1)).findById(brandId);
    }

}