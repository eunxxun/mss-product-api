package com.eunsun.mssproductapi.api.v1.product.dto;

import com.eunsun.mssproductapi.common.exception.ErrorMessages;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(
        @NotNull(message = ErrorMessages.NULL_BRAND_ID)
        @Positive(message = ErrorMessages.NOT_POSITIVE_ID)
        Long brandId,

        @NotNull(message = ErrorMessages.NULL_CATEGORY_ID)
        @Positive(message = ErrorMessages.NOT_POSITIVE_ID)
        Long categoryId,

        @NotBlank(message = ErrorMessages.BLANK_PRODUCE_NM)
        @Size(max = 255, message = ErrorMessages.MAX_LEN_PRODUCT_NM)
        String productNm,

        @NotNull(message = ErrorMessages.NULL_PRICE)
        @DecimalMin(value = "1.0", inclusive = false, message = ErrorMessages.MIN_PRICE)
        BigDecimal price) {
}