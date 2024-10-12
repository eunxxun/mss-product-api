package com.eunsun.mssproductapi.domain.product.entity;

import com.eunsun.mssproductapi.domain.brand.entity.Brand;
import com.eunsun.mssproductapi.domain.category.entity.Category;
import com.eunsun.mssproductapi.common.util.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SoftDelete;

import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCT")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@SoftDelete(columnName = "del_yn")
public class Product extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "product_nm", nullable = false)
    private String productNm;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "del_yn", insertable = false, updatable = false)
    private Boolean delYn = false;

    public void update(Brand brand, Category category, String productName, BigDecimal price) {
        this.brand = brand;
        this.category = category;
        this.productNm = productName;
        this.price = price;
    }

}

