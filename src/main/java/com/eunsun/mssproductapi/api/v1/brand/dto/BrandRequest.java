package com.eunsun.mssproductapi.api.v1.brand.dto;

import com.eunsun.mssproductapi.common.exception.ErrorMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BrandRequest(
        @NotBlank(message = ErrorMessages.BLANK_BRAND_NM)
        @Size(max = 100, message = ErrorMessages.MAX_LEN_BRAND_NM)
        String brandNm) {
}
