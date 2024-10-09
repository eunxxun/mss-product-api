package com.eunsun.mssproductapi.product.repository;

import com.eunsun.mssproductapi.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
