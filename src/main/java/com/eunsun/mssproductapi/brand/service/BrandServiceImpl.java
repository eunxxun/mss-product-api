package com.eunsun.mssproductapi.brand.service;

import com.eunsun.mssproductapi.brand.dto.BrandRequest;
import com.eunsun.mssproductapi.brand.dto.BrandResponse;
import com.eunsun.mssproductapi.brand.entity.Brand;
import com.eunsun.mssproductapi.brand.mapper.BrandMapper;
import com.eunsun.mssproductapi.brand.repository.BrandRepository;
import com.eunsun.mssproductapi.common.exception.ExceptionMessage;
import com.eunsun.mssproductapi.common.exception.DuplicationException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    @Override
    public BrandResponse create(BrandRequest brandRequest) {
        Brand brand = brandRepository.save(BrandMapper.toEntity(brandRequest));
        return BrandMapper.toResponse(brand);
    }

    @Override
    public BrandResponse update(Long id, BrandRequest brandRequest) {
        Brand brand = findById(id);
        if (!brand.isSameName(brandRequest.brandNm())) {
            validateUniqueBrandNm(brandRequest.brandNm());
        }
        brand.updateBrandNm(brandRequest.brandNm());
        brandRepository.save(brand);
        return BrandMapper.toResponse(brand);
    }

    @Override
    public void delete(Long id) {
        Brand brand = findById(id);
        brandRepository.delete(brand);
    }

    @Override
    public Brand findById(Long id) {
        return brandRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ExceptionMessage.NOT_EXISTS_BRAND_ID));
    }

    private void validateUniqueBrandNm(String brandNm) {
        if (brandRepository.existsByBrandNm(brandNm)) {
            throw new DuplicationException(ExceptionMessage.EXISTS_BRAND_NM + brandNm);
        }
    }
}
