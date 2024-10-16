package com.eunsun.mssproductapi.api.v1.product;

import com.eunsun.mssproductapi.api.v1.product.dto.ProductRequest;
import com.eunsun.mssproductapi.api.v1.product.dto.ProductResponse;
import com.eunsun.mssproductapi.domain.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "상품")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @Operation(summary = "상품 생성", description = "신규 상품을 생성합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        ProductResponse response = productService.create(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PutMapping("/{productId}")
    @Operation(summary = "상품 수정", description = "상품 정보를 수정합니다.")
    public ResponseEntity<ProductResponse> updateBrand(@PathVariable Long productId, @Valid @RequestBody ProductRequest productRequest) {
        ProductResponse response = productService.update(productId, productRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteBrand(@PathVariable Long productId) {
        productService.delete(productId);
        return ResponseEntity.noContent().build();
    }

}
