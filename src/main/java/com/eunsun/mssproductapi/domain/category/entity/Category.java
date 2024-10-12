package com.eunsun.mssproductapi.domain.category.entity;

import com.eunsun.mssproductapi.common.util.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CATEGORY")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Category extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_nm", nullable = false)
    private String categoryNm;
}
