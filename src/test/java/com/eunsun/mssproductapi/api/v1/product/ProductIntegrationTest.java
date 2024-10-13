package com.eunsun.mssproductapi.api.v1.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("상품 생성 테스트")
    void createProductTest() throws Exception {
        String productRequestJson = "{\"brandId\":1, \"categoryId\":1, \"productNm\":\"신규상품\", \"price\":10000}";

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productNm").value("신규상품"))
                .andExpect(jsonPath("$.price").value(10000));
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void updateProductTest() throws Exception {
        Long productId = 1L;
        String productRequestJson = "{\"brandId\":1, \"categoryId\":1, \"productNm\":\"수정상품\", \"price\":20000}";

        mockMvc.perform(put("/api/v1/products/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productNm").value("수정상품"))
                .andExpect(jsonPath("$.price").value(20000));
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteProductTest() throws Exception {
        Long productId = 1L;

        mockMvc.perform(delete("/api/v1/products/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}