package com.eunsun.mssproductapi.domain.brand.service;

import com.eunsun.mssproductapi.api.v1.brand.dto.BrandRequest;
import com.eunsun.mssproductapi.api.v1.brand.dto.BrandResponse;
import com.eunsun.mssproductapi.domain.brand.entity.Brand;
import com.eunsun.mssproductapi.domain.brand.mapper.BrandMapper;
import com.eunsun.mssproductapi.domain.brand.repository.BrandRepository;
import com.eunsun.mssproductapi.common.exception.ExceptionMessage;
import com.eunsun.mssproductapi.common.exception.DuplicationException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    /**
     * 브랜드 생성
     * @param brandRequest 신규 브랜드 정보
     * @return BrandResponse
     */
    @Override
    @Transactional
    public BrandResponse create(BrandRequest brandRequest) {
        validateUniqueBrandNm(brandRequest.brandNm());
        Brand brand = brandRepository.save(BrandMapper.toEntity(brandRequest));
        return BrandMapper.toResponse(brand);
    }

    /**
     * 브랜드 정보 수정
     * @param id 브랜드ID
     * @param brandRequest 수정 브랜드 정보
     * @return BrandResponse
     */
    @Override
    @Transactional
    public BrandResponse update(Long id, BrandRequest brandRequest) {
        Brand brand = findById(id);
        if (!brand.isSameName(brandRequest.brandNm())) {
            validateUniqueBrandNm(brandRequest.brandNm());
        }
        brand.update(brandRequest.brandNm());
        brandRepository.save(brand);
        return BrandMapper.toResponse(brand);
    }

    /**
     * 브랜드 삭제
     * @param id 브랜드ID
     */
    @Override
    @Transactional
    public void delete(Long id) {
        Brand brand = findById(id);
        brandRepository.delete(brand);
    }

    /**
     * 브랜드ID 조회
     * @param id 브랜드ID
     * @return Brand
     */
    @Override
    public Brand findById(Long id) {
        return brandRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_BRAND));
    }

    private void validateUniqueBrandNm(String brandNm) {
        if (brandRepository.existsByBrandNm(brandNm)) {
            throw new DuplicationException(ExceptionMessage.EXISTS_BRAND_NM + brandNm);
        }
    }
}
