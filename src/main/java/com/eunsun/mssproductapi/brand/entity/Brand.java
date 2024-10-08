package com.eunsun.mssproductapi.brand.entity;

import com.eunsun.mssproductapi.common.util.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BRAND")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Brand extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand_nm", nullable = false)
    private String brandNm;

    public void updateBrandNm(String newBrandNm) {
        this.brandNm = newBrandNm;
    }

    public boolean isSameName(String newBrandNm) {
        return this.brandNm.equals(newBrandNm);
    }
}
