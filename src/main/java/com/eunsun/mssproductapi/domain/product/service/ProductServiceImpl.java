package com.eunsun.mssproductapi.domain.product.service;

import com.eunsun.mssproductapi.common.exception.DuplicationException;
import com.eunsun.mssproductapi.domain.brand.entity.Brand;
import com.eunsun.mssproductapi.domain.brand.service.BrandService;
import com.eunsun.mssproductapi.domain.category.service.CategoryService;
import com.eunsun.mssproductapi.domain.category.entity.Category;
import com.eunsun.mssproductapi.common.exception.ExceptionMessage;
import com.eunsun.mssproductapi.api.v1.product.dto.ProductRequest;
import com.eunsun.mssproductapi.api.v1.product.dto.ProductResponse;
import com.eunsun.mssproductapi.domain.product.repository.ProductRepository;
import com.eunsun.mssproductapi.domain.product.entity.Product;
import com.eunsun.mssproductapi.domain.product.mapper.ProductMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    @Caching(evict = {
            @CacheEvict(value = "categoryLowestPrices", key = "'ctgLowestKey'"),
            @CacheEvict(value = "brandLowestPrices", key = "'brdLowestKey'"),
            @CacheEvict(value = "categoryRangePrices", key = "#productRequest.categoryId()")
    })
    public ProductResponse create(ProductRequest productRequest) {
        Brand brand = brandService.findById(productRequest.brandId());
        Category category = categoryService.findCategoryById(productRequest.categoryId());

        if (isExistEqualProduct(brand, category, productRequest)) {
            throw new DuplicationException(ExceptionMessage.EXISTS_PRODUCT);
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
    @Caching(evict = {
            @CacheEvict(value = "categoryLowestPrices", key = "'ctgLowestKey'"),
            @CacheEvict(value = "brandLowestPrices", key = "'brdLowestKey'"),
            @CacheEvict(value = "categoryRangePrices", key = "#productRequest.categoryId()")
    })
    public ProductResponse update(Long id, ProductRequest productRequest) {
        Product product = findById(id);

        Brand brand = brandService.findById(productRequest.brandId());
        Category category = categoryService.findCategoryById(productRequest.categoryId());

        if (isExistEqualProduct(brand, category, productRequest)) {
            throw new DuplicationException(ExceptionMessage.EXISTS_PRODUCT);
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
    @Caching(evict = {
            @CacheEvict(value = "categoryLowestPrices", key = "'ctgLowestKey'"),
            @CacheEvict(value = "brandLowestPrices", key = "'brdLowestKey'"),
            @CacheEvict(value = "categoryRangePrices", allEntries = true)
    })
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
                () -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_PRODUCT));
    }

    private boolean isExistEqualProduct(Brand brand, Category category, ProductRequest request) {
        return productRepository.existsByBrandAndCategoryAndProductNmAndPrice(brand, category, request.productNm(), request.price());
    }

}
