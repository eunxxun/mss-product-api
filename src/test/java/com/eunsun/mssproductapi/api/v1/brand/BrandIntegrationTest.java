package com.eunsun.mssproductapi.api.v1.brand;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BrandIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("브랜드 생성 테스트")
    void createBrandTest() throws Exception {
        String brandRequestJson = "{\"brandNm\":\"신규브랜드\"}";

        mockMvc.perform(post("/api/v1/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandRequestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.brandNm").value("신규브랜드"));
    }

    @Test
    @DisplayName("브랜드 수정 테스트")
    void updateBrandTest() throws Exception {
        Long brandId = 1L;
        String brandRequestJson = "{\"brandNm\":\"수정브랜드\"}";

        mockMvc.perform(put("/api/v1/brands/{brandId}", brandId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brandNm").value("수정브랜드"));
    }

    @Test
    @DisplayName("브랜드 삭제 테스트")
    void deleteBrandTest() throws Exception {
        Long brandId = 1L;

        mockMvc.perform(delete("/api/v1/brands/{brandId}", brandId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}