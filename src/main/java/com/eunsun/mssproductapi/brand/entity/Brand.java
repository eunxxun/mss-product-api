package com.eunsun.mssproductapi.brand.entity;

import com.eunsun.mssproductapi.common.util.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SoftDelete;

@Entity
@Table(name = "BRAND")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@SoftDelete(columnName = "del_yn")
public class Brand extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand_nm", nullable = false)
    private String brandNm;

    @Column(name = "del_yn", insertable = false, updatable = false)
    private Boolean delYn = false;

    public boolean isSameName(String newBrandNm) {
        return this.brandNm.equals(newBrandNm);
    }

    public void update(String newBrandNm) {
        this.brandNm = newBrandNm;
    }

}



