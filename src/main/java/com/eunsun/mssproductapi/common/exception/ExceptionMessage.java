package com.eunsun.mssproductapi.common.exception;

import lombok.Getter;

@Getter
public class ExceptionMessage {
    final public static String NOT_FOUND_BRAND = "브랜드를 찾을 수 없습니다.";
    final public static String NOT_FOUND_CATEGORY = "카테고리를 찾을 수 없습니다.";
    final public static String NOT_FOUND_PRODUCT = "상품을 찾을 수 없습니다.";
    final public static String NOT_FOUND_LOWEST_BRAND = "최저가 브랜드를 찾을 수 없습니다.";

    final public static String EXISTS_BRAND_NM = "이미 존재하는 브랜드명입니다: ";
}
