package com.eunsun.mssproductapi.domain.product.service;

import com.eunsun.mssproductapi.api.v1.product.dto.ProductRequest;
import com.eunsun.mssproductapi.api.v1.product.dto.ProductResponse;

public interface ProductService {
    ProductResponse create(ProductRequest productRequest);
    ProductResponse update(Long id, ProductRequest productRequest);
    void delete(Long id);

    void deleteByBrandId(Long brandId);
}
