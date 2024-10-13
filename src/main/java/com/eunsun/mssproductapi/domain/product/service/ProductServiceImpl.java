package com.eunsun.mssproductapi.domain.product.service;

import com.eunsun.mssproductapi.api.v1.product.dto.ProductRequest;
import com.eunsun.mssproductapi.api.v1.product.dto.ProductResponse;
import com.eunsun.mssproductapi.common.exception.DuplicationException;
import com.eunsun.mssproductapi.common.exception.ErrorMessages;
import com.eunsun.mssproductapi.domain.brand.entity.Brand;
import com.eunsun.mssproductapi.domain.brand.service.BrandService;
import com.eunsun.mssproductapi.domain.category.entity.Category;
import com.eunsun.mssproductapi.domain.category.service.CategoryService;
import com.eunsun.mssproductapi.domain.product.entity.Product;
import com.eunsun.mssproductapi.domain.product.mapper.ProductMapper;
import com.eunsun.mssproductapi.domain.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final BrandService brandService;
    private final CategoryService categoryService;

    /**
     * 상품 생성
     * @param productRequest 신규 상품 정보
     * @return ProductResponse
     */
    @Override
    @Transactional
    public ProductResponse create(ProductRequest productRequest) {
        Brand brand = brandService.findById(productRequest.brandId());
        Category category = categoryService.findCategoryById(productRequest.categoryId());

        if (isExistEqualProduct(brand, category, productRequest)) {
            throw new DuplicationException(ErrorMessages.EXISTS_PRODUCT);
        }
        Product product = productRepository.save(ProductMapper.toEntity(productRequest, brand, category));
        return ProductMapper.toResponse(product);
    }

    /**
     * 상품 정보 수정
     * @param id 상품ID
     * @param productRequest 수정 상품 정보
     * @return ProductResponse
     */
    @Override
    @Transactional
    public ProductResponse update(Long id, ProductRequest productRequest) {
        Product product = findWithBrandAndCategoryById(id);

        Brand brand = product.getBrand();
        Category category = product.getCategory();

        if (isExistEqualProduct(brand, category, productRequest)) {
            throw new DuplicationException(ErrorMessages.EXISTS_PRODUCT);
        }

        product.update(brand, category, productRequest.productNm(), productRequest.price());
        productRepository.save(product);
        return ProductMapper.toResponse(product);
    }

    /**
     * 상품 삭제
     * @param id 상품ID
     */
    @Override
    @Transactional
    public void delete(Long id) {
        Product product = findById(id);
        productRepository.delete(product);
    }

    @Override
    public void deleteByBrandId(Long brandId) {
        productRepository.softDeleteProductsByBrand(brandId);
    }

    private Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessages.NOT_FOUND_PRODUCT));
    }

    private Product findWithBrandAndCategoryById(Long id) {
        return productRepository.findWithBrandAndCategoryById(id).orElseThrow(
                () -> new EntityNotFoundException(ErrorMessages.NOT_FOUND_PRODUCT));
    }

    private boolean isExistEqualProduct(Brand brand, Category category, ProductRequest request) {
        return productRepository.existsByBrandAndCategoryAndProductNmAndPrice(brand, category, request.productNm(), request.price());
    }

}
