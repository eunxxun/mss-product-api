package com.eunsun.mssproductapi.api.v1.brand;

import com.eunsun.mssproductapi.api.v1.brand.dto.BrandResponse;
import com.eunsun.mssproductapi.domain.brand.service.BrandService;
import com.eunsun.mssproductapi.api.v1.brand.dto.BrandRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "브랜드")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/brands")
public class BrandController {
    private final BrandService brandService;

    @PostMapping
    @Operation(summary = "브랜드 생성", description = "신규 브랜드를 생성합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BrandResponse> createBrand(@Valid @RequestBody BrandRequest brandRequest) {
        BrandResponse response = brandService.create(brandRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PutMapping("/{brandId}")
    @Operation(summary = "브랜드 수정", description = "브랜드명을 수정합니다.")
    public ResponseEntity<BrandResponse> updateBrand(@PathVariable Long brandId, @Valid @RequestBody BrandRequest brandRequest) {
        BrandResponse response = brandService.update(brandId, brandRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{brandId}")
    @Operation(summary = "브랜드 삭제", description = "브랜드를 삭제합니다.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteBrand(@PathVariable Long brandId) {
        brandService.delete(brandId);
        return ResponseEntity.noContent().build();
    }

}
