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

    @Column(name = "del_yn", nullable = false)
    private Boolean delYn = false;

    @PrePersist
    public void prePersist() {
        if (this.delYn == null) {
            this.delYn = false;
        }
    }

    public boolean isSameName(String newBrandNm) {
        return this.brandNm.equals(newBrandNm);
    }

    public void update(String newBrandNm) {
        this.brandNm = newBrandNm;
    }

    public void delete() {
        this.delYn = true;
    }
}
