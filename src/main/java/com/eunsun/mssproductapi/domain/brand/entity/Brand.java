package com.eunsun.mssproductapi.domain.brand.entity;

import com.eunsun.mssproductapi.common.util.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "BRAND")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@SQLRestriction("del_yn = false")
@SQLDelete(sql = "UPDATE brand SET del_yn = true, updated_at = CURRENT_DATE where id = ?")
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

}



