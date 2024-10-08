package com.eunsun.mssproductapi.product.mapper;

import com.eunsun.mssproductapi.brand.entity.Brand;
import com.eunsun.mssproductapi.category.entity.Category;
import com.eunsun.mssproductapi.product.dto.ProductRequest;
import com.eunsun.mssproductapi.product.dto.ProductResponse;
import com.eunsun.mssproductapi.product.entity.Product;

public class ProductMapper {
    public static Product toEntity(ProductRequest productRequest, Brand brand, Category category) {
        return Product.builder()
                .brand(brand)
                .category(category)
                .productName(productRequest.productNm())
                .price(productRequest.price())
                .build();
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(product.getId(), product.getBrand().getBrandNm(), product.getCategory().getCategoryNm(), product.getProductName(), product.getPrice());
    }

}
