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

    @Override
    public ProductResponse create(ProductRequest productRequest) {
        Brand brand = brandService.findById(productRequest.brandId());
        Category category = categoryService.findCategoryById(productRequest.categoryId());

        Product product = productRepository.save(ProductMapper.toEntity(productRequest, brand, category));
        return ProductMapper.toResponse(product);
    }

    @Override
    public ProductResponse update(Long id, ProductRequest productRequest) {
        Product product = findById(id);

        Brand brand = brandService.findById(productRequest.brandId());
        Category category = categoryService.findCategoryById(productRequest.categoryId());

        product.update(brand, category, productRequest.productNm(), productRequest.price());
        productRepository.save(product);
        return ProductMapper.toResponse(product);
    }

    @Override
    public void delete(Long id) {
        Product product = findById(id);
        productRepository.delete(product);
    }

    private Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(ExceptionMessage.NOT_EXISTS_PRD_ID));
    }

}
