package com.eunsun.mssproductapi.domain.brand.service;

import com.eunsun.mssproductapi.domain.brand.dto.BrandRequest;
import com.eunsun.mssproductapi.domain.brand.mapper.BrandMapper;
import com.eunsun.mssproductapi.domain.brand.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    public void createBrand(BrandRequest brandRequest) {
        brandRepository.save(BrandMapper.toEntity(brandRequest));
    }
}
