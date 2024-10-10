DROP TABLE IF EXISTS CATEGORY;
DROP TABLE IF EXISTS BRAND;
DROP TABLE IF EXISTS PRODUCT;

-- CATEGORY 테이블
CREATE TABLE CATEGORY
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    category_nm VARCHAR(255) NOT NULL UNIQUE COMMENT '카테고리명',
    created_at  DATETIME     NOT NULL,
    updated_at  DATETIME     NOT NULL
);

CREATE UNIQUE INDEX idx_category_nm ON CATEGORY (category_nm);

-- BRAND 테이블
CREATE TABLE BRAND
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    brand_nm   VARCHAR(255) NOT NULL UNIQUE COMMENT '브랜드명',
    del_yn     BOOLEAN      NOT NULL COMMENT '삭제여부',
    created_at DATETIME     NOT NULL,
    updated_at DATETIME     NOT NULL
);

CREATE UNIQUE INDEX idx_brand_nm ON BRAND (brand_nm);
CREATE INDEX idx_brand_del_yn ON BRAND (del_yn);

-- PRODUCT 테이블
CREATE TABLE PRODUCT
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    brand_id    INT            NOT NULL COMMENT '브랜드 ID',
    category_id INT            NOT NULL COMMENT '카테고리 ID',
    product_nm  VARCHAR(255)   NOT NULL COMMENT '상품명',
    price       DECIMAL(10, 2) NOT NULL COMMENT '가격',
    del_yn      BOOLEAN        NOT NULL COMMENT '삭제여부',
    created_at  DATETIME       NOT NULL,
    updated_at  DATETIME       NOT NULL,
    FOREIGN KEY (brand_id) REFERENCES BRAND (id),
    FOREIGN KEY (category_id) REFERENCES CATEGORY (id)
);

CREATE INDEX idx_product_brand_id ON PRODUCT (brand_id);
CREATE INDEX idx_product_category_id ON PRODUCT (category_id);
CREATE INDEX idx_product_price ON PRODUCT (price);
CREATE INDEX idx_product_del_yn ON PRODUCT (del_yn);

--카테고리별 최저가 상품 조회를 위해 인덱스 추가
CREATE INDEX idx_product_category_price_delyn ON PRODUCT (category_id, price, del_yn);
CREATE INDEX idx_product_brand_category_price ON PRODUCT (brand_id, category_id, price);

-- CATEGORY 데이터 추가
insert into CATEGORY values (1, '상의', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into CATEGORY values (2, '아우터', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into CATEGORY values (3, '바지', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into CATEGORY values (4, '스니커즈', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into CATEGORY values (5, '가방', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into CATEGORY values (6, '모자', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into CATEGORY values (7, '양말', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into CATEGORY values (8, '액세서리', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

-- BRAND 데이터 추가
insert into BRAND values (1, '무신사 스탠다드', false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into BRAND values (2, '디즈이즈네버댓', false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into BRAND values (3, '예일', false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into BRAND values (4, '아디다스', false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into BRAND values (5, '노스페이스',false,  CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into BRAND values (6, '리', false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into BRAND values (7, '커버낫', false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into BRAND values (8, '마리떼', false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into BRAND values (9, '푸마', false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
ALTER TABLE BRAND ALTER COLUMN ID RESTART WITH 10;

-- Goods 데이터 추가
insert into PRODUCT values (1, 1, 1, 'prd_a_상의', 11200, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (2, 1, 2, 'prd_a_아우터', 5500, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (3, 1, 3, 'prd_a_바지', 4200, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (4, 1, 4, 'prd_a_스니커즈', 9000, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (5, 1, 5, 'prd_a_가방', 2000, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (6, 1, 6, 'prd_a_모자', 1700, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (7, 1, 7, 'prd_a_양말', 1800, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (8, 1, 8, 'prd_a_액세서리', 2300, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (9, 2, 1, 'prd_b_상의', 10500, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (10, 2, 2, 'prd_b_아우터', 5900, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (11, 2, 3, 'prd_b_바지', 3800, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (12, 2, 4, 'prd_b_스니커즈', 9100, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (13, 2, 5, 'prd_b_가방', 2100, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (14, 2, 6, 'prd_b_모자', 2000, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (15, 2, 7, 'prd_b_양말', 2000, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (16, 2, 8, 'prd_b_액세서리', 2200, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (17, 3, 1, 'prd_c_상의', 10000, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (18, 3, 2, 'prd_c_아우터', 6200, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (19, 3, 3, 'prd_c_바지', 3300, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (20, 3, 4, 'prd_c_스니커즈', 9200, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (21, 3, 5, 'prd_c_가방', 2200, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (22, 3, 6, 'prd_c_모자', 1900, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (23, 3, 7, 'prd_c_양말', 2200, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (24, 3, 8, 'prd_c_액세서리', 2100, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (25, 4, 1, 'prd_d_상의', 10100, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (26, 4, 2, 'prd_d_아우터', 5100, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (27, 4, 3, 'prd_d_바지', 3000, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (28, 4, 4, 'prd_d_스니커즈', 9500, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (29, 4, 5, 'prd_d_가방', 2500, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (30, 4, 6, 'prd_d_모자', 1500, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (31, 4, 7, 'prd_d_양말', 2400, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (32, 4, 8, 'prd_d_액세서리', 2000, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (33, 5, 1, 'prd_e_상의', 10700, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (34, 5, 2, 'prd_e_아우터', 5000, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (35, 5, 3, 'prd_e_바지', 3800, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (36, 5, 4, 'prd_e_스니커즈', 9900, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (37, 5, 5, 'prd_e_가방', 2300, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (38, 5, 6, 'prd_e_모자', 1800, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (39, 5, 7, 'prd_e_양말', 2100, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (40, 5, 8, 'prd_e_액세서리', 2100, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (41, 6, 1, 'prd_f_상의', 11200, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (42, 6, 2, 'prd_f_아우터', 7200, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (43, 6, 3, 'prd_f_바지', 4000, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (44, 6, 4, 'prd_f_스니커즈', 9300, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (45, 6, 5, 'prd_f_가방', 2100, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (46, 6, 6, 'prd_f_모자', 1600, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (47, 6, 7, 'prd_f_양말', 2300, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (48, 6, 8, 'prd_f_액세서리', 1900, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (49, 7, 1, 'prd_g_상의', 10500, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (50, 7, 2, 'prd_g_아우터', 5800, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (51, 7, 3, 'prd_g_바지', 3900, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (52, 7, 4, 'prd_g_스니커즈', 9000, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (53, 7, 5, 'prd_g_가방', 2200, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (54, 7, 6, 'prd_g_모자', 1700, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (55, 7, 7, 'prd_g_양말', 2100, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (56, 7, 8, 'prd_g_액세서리', 2000, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (57, 8, 1, 'prd_h_상의', 10800, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (58, 8, 2, 'prd_h_아우터', 6300, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (59, 8, 3, 'prd_h_바지', 3100, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (60, 8, 4, 'prd_h_스니커즈', 9700, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (61, 8, 5, 'prd_h_가방', 2100, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (62, 8, 6, 'prd_h_모자', 1600, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (63, 8, 7, 'prd_h_양말', 2000, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (64, 8, 8, 'prd_h_액세서리', 2000, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (65, 9, 1, 'prd_i_상의', 11400, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (66, 9, 2, 'prd_i_아우터', 6700, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (67, 9, 3, 'prd_i_바지', 3200, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (68, 9, 4, 'prd_i_스니커즈', 9500, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (69, 9, 5, 'prd_i_가방', 2400, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (70, 9, 6, 'prd_i_모자', 1700, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (71, 9, 7, 'prd_i_양말', 1700, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
insert into PRODUCT values (72, 9, 8, 'prd_i_액세서리', 2400, false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
ALTER TABLE PRODUCT ALTER COLUMN ID RESTART WITH 73;


