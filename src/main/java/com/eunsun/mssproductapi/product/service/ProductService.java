package com.eunsun.mssproductapi.product.service;

import com.eunsun.mssproductapi.product.dto.ProductRequest;
import com.eunsun.mssproductapi.product.dto.ProductResponse;

public interface ProductService {
    ProductResponse create(ProductRequest productRequest);
    ProductResponse update(Long id, ProductRequest productRequest);
    void delete(Long id);
}
