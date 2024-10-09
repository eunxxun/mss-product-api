package com.eunsun.mssproductapi.product.service;

import com.eunsun.mssproductapi.brand.entity.Brand;
import com.eunsun.mssproductapi.brand.service.BrandService;
import com.eunsun.mssproductapi.category.Service.CategoryService;
import com.eunsun.mssproductapi.category.entity.Category;
import com.eunsun.mssproductapi.common.exception.ExceptionMessage;
import com.eunsun.mssproductapi.product.dto.ProductRequest;
import com.eunsun.mssproductapi.product.dto.ProductResponse;
import com.eunsun.mssproductapi.product.entity.Product;
import com.eunsun.mssproductapi.product.mapper.ProductMapper;
import com.eunsun.mssproductapi.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
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
    public ProductResponse create(ProductRequest productRequest) {
        Brand brand = brandService.findById(productRequest.brandId());
        Category category = categoryService.findCategoryById(productRequest.categoryId());

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
    public ProductResponse update(Long id, ProductRequest productRequest) {
        Product product = findById(id);

        Brand brand = brandService.findById(productRequest.brandId());
        Category category = categoryService.findCategoryById(productRequest.categoryId());

        product.update(brand, category, productRequest.productNm(), productRequest.price());
        productRepository.save(product);
        return ProductMapper.toResponse(product);
    }

    /**
     * 상품 삭제
     * @param id 상품ID
     */
    @Override
    public void delete(Long id) {
        Product product = findById(id);

        product.delete();
        productRepository.save(product);
    }

    private Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_PRODUCT));
    }

}
