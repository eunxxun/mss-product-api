package com.eunsun.mssproductapi.api.v1.product.dto;

import com.eunsun.mssproductapi.common.exception.ExceptionMessage;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(
        @NotNull(message = ExceptionMessage.NULL_BRAND_ID)
        @Positive(message = ExceptionMessage.NOT_POSITIVE_ID)
        Long brandId,

        @NotNull(message = ExceptionMessage.NULL_CATEGORY_ID)
        @Positive(message = ExceptionMessage.NOT_POSITIVE_ID)
        Long categoryId,

        @NotBlank(message = ExceptionMessage.BLANK_PRODUCE_NM)
        @Size(max = 255, message = ExceptionMessage.MAX_LEN_PRODUCT_NM)
        String productNm,

        @NotNull(message = ExceptionMessage.NULL_PRICE)
        @DecimalMin(value = "1.0", inclusive = false, message = ExceptionMessage.MIN_PRICE)
        BigDecimal price) {
}