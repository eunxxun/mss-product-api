package com.eunsun.mssproductapi.api.v1.price;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PriceIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("카테고리별 최저가 브랜드, 상품, 총액 조회 테스트")
    void getCategoryLowestPricesTest() throws Exception {
        mockMvc.perform(get("/api/v1/prices/categories/lowest")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lowestPriceCategoryList").exists())
                .andExpect(jsonPath("$.totalPrice").exists());
    }

    @Test
    @DisplayName("단일 브랜드 전체 카테고리 최저가 상품총액 조회 테스트")
    void getBrandLowestPricesTest() throws Exception {
        mockMvc.perform(get("/api/v1/prices/brands/lowest")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brandNm").exists())
                .andExpect(jsonPath("$.categories").isArray())
                .andExpect(jsonPath("$.totalPrice").exists());
    }

    @Test
    @DisplayName("카테고리명으로 최저, 최고 가격 브랜드와 상품 가격 조회 테스트")
    void getCategoryPriceRangeTest() throws Exception {
        String categoryNm = "아우터";

        mockMvc.perform(get("/api/v1/prices/category/{categoryNm}/range", categoryNm)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryNm").value(categoryNm))
                .andExpect(jsonPath("$.lowestPrices").isArray())
                .andExpect(jsonPath("$.highestPrices").isArray());
    }
}