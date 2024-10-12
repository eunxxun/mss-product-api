package com.eunsun.mssproductapi.common.exception;

import lombok.Getter;

@Getter
public class ExceptionMessage {
    // Not Found
    final public static String NOT_FOUND_BRAND = "브랜드를 찾을 수 없습니다.";
    final public static String NOT_FOUND_CATEGORY = "카테고리를 찾을 수 없습니다.";
    final public static String NOT_FOUND_PRODUCT = "상품을 찾을 수 없습니다.";
    final public static String NOT_FOUND_LOWEST_BRAND = "최저가 브랜드를 찾을 수 없습니다.";

    // Duration
    final public static String EXISTS_BRAND_NM = "이미 존재하는 브랜드명입니다: ";
    final public static String EXISTS_PRODUCT = "이미 존재하는 상품입니다.";

    // Request Validation
    final public static String BLANK_BRAND_NM = "브랜드명을 입력해주세요.";
    final public static String MAX_LEN_BRAND_NM = "브랜드명은 최대100자 입니다.";

    final public static String NOT_POSITIVE_ID = "ID는 양수입니다.";
    final public static String NULL_BRAND_ID = "브랜드 ID를 입력해주세요.";
    final public static String NULL_CATEGORY_ID = "카테고리 ID를 입력해주세요.";
    final public static String BLANK_PRODUCE_NM = "상품명을 입력해주세요.";
    final public static String MAX_LEN_PRODUCT_NM = "상품명은 최대255자 입니다.";
    final public static String NULL_PRICE = "가격을 입력해주세요.";
    final public static String MIN_PRICE = "가격을 1원이상 입력해주세요.";
}
