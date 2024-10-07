package com.eunsun.mssproductapi.domain.brand.controller;

import com.eunsun.mssproductapi.domain.brand.dto.BrandRequest;
import com.eunsun.mssproductapi.domain.brand.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/brand")
public class BrandController {
    private final BrandService brandService;

    @PostMapping
    @Operation(summary = "브랜드 생성", description = "신규 브랜드 생성")
    public ResponseEntity<String> createBrand(@Valid @RequestBody BrandRequest brandRequest) {
        brandService.createBrand(brandRequest);
        return ResponseEntity.ok("Success");
    }

}
