package com.eunsun.mssproductapi.common.aop;

import com.eunsun.mssproductapi.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class BrandAspect {
    private final ProductService productService;

    @AfterReturning(value = "execution(* com.eunsun.mssproductapi.brand.service.BrandService.delete(..)) && args(brandId)", argNames = "brandId")
    public void deleteProductAfterBrandDelete(Long brandId) {
        productService.deleteByBrandId(brandId);
    }
}
