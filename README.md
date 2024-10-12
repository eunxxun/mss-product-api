# 상품 관리 및 가격비교 API

## 개발 환경

- JAVA: 21
- Framework : Springboot v3.3.4
- Build Tool : Gradle v8.10
- Database : H2
- Etc : Swagger

## 빌드 및 실행 방법
```bash
git clone https://github.com/eunxxun/mss-product-api.git
```
```bash
./gradlew clean build
```
```bash
./gradlew bootRun
```
## H2 데이터베이스 접속정보
```bash
H2 콘솔 : http://localhost:8080/h2-console
JDBC URL : jdbc:h2:mem:commercedb
User Name : sa
(no password)
```
## API 명세 (Swagger)
- http://localhost:8080/swagger-ui/index.html/

## DB 구성
- H2를 사용한 메모리 DB로 설정
- 데이터베이스 Schema 및 Data 초기 설정을 위해 data.sql 작성

## 구현 설명
### 1. **GET /api/v1/prices/categories/lowest**
카테고리 별 최저가격 브랜드와 상품 가격, 총액 조회

**Request**:
- Method: `GET`
- Endpoint: `/api/v1/prices/categories/lowest`

**Response**:
- Status: `200 OK`
- Body:
    ```json
    {
      "lowestPriceCategoryInterfaceList": [
        {"brandNm": "무신사 스탠다드", "price": 9000, "categoryNm": "스니커즈"},
        {"brandNm": "무신사 스탠다드", "price": 2000, "categoryNm": "가방"},
        {"brandNm": "예일", "price": 10000, "categoryNm": "상의"},
        {"brandNm": "아디다스", "price": 3000, "categoryNm": "바지"},
        {"brandNm": "아디다스", "price": 1500, "categoryNm": "모자"},
        {"brandNm": "노스페이스", "price": 5000, "categoryNm": "아우터"},
        {"brandNm": "리", "price": 1900, "categoryNm": "액세서리"},
        {"brandNm": "푸마", "price": 1700, "categoryNm": "양말"}
      ],
      "totalPrice": 34100
    }
    ```
### 2. **GET /api/v1/prices/brands/lowest**
단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액 조회

**Request**:
- Method: `GET`
- Endpoint: `/api/v1/prices/brands/lowest`

**Response**:
- Status: `200 OK`
- Body:
  ```json
  {
    "brandNm": "무신사 스탠다드",
    "categories": [
      {"categoryNm": "가방", "price": 2000},
      {"categoryNm": "모자", "price": 1700},
      {"categoryNm": "바지", "price": 4200},
      {"categoryNm": "스니커즈", "price": 9000},
      {"categoryNm": "아우터", "price": 5500},
      {"categoryNm": "액세서리", "price": 2300},
      {"categoryNm": "양말", "price": 1800}
    ],
    "totalPrice": 26500
  }
  ```

### 3. **GET /api/v1/prices/category/{categoryNm}/range**
카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격 조회

**Request**:
- Method: `GET`
- Endpoint: `/api/v1/prices/category/{categoryNm}/range`

**Path Parameter**:
- `categoryNm`: 카테고리명

**Response**:
- Status: `200 OK`
- Body:
  ```json
  {
    "categoryNm": "모자",
    "lowestPrices": [
      {"brandNm": "아디다스", "price": 1500}
    ],
    "highestPrices": [
      {"brandNm": "예일", "price": 1900}
    ]
  }
   ```

### 4. **POST /api/v1/brands**
신규 브랜드 생성

**Request**:
- Method: `POST`
- Endpoint: `/api/v1/brands`
- Body:
  ```json
  {
    "brandNm": "잔스포츠"
  }
  ```

**Response**:
- Status: `201 Created`
- Body:
  ```json
  {
    "id": 10,
    "brandNm": "잔스포츠"
  }
  ```

### 5. **PUT /api/v1/brands/{brandId}**
브랜드 수정

**Request**:
- Method: `PUT`
- Endpoint: `/api/v1/brands/{brandId}`
- Body:
  ```json
  {
    "brandNm": "나이키"
  }
  ```

**Response**:
- Status: `200 OK`
- Body:
  ```json
  {
    "id": 10,
    "brandNm": "나이키"
  }
  ```

### 6. **DELETE /api/v1/brands/{brandId}**
브랜드 삭제

**Request**:
- Method: `DELETE`
- Endpoint: `/api/v1/brands/{brandId}`

**Response**:
- Status: `204 No Content`

### 7. **POST /api/v1/products**
신규 상품 생성

**Request**:
- Method: `POST`
- Endpoint: `/api/v1/products`
- Body:
  ```json
  {
    "brandId": 1,
    "categoryId": 1,
    "productNm": "스트라이프 티셔츠",
    "price": 50000
  }
  ```

**Response**:
- Status: `201 Created`
- Body:
  ```json
  {
    "id": 74,
    "brandNm": "무신사 스탠다드",
    "CategoryNm": "상의",
    "ProductNm": "스트라이프 티셔츠",
    "price": 50000
  }
  ```

### 8. **PUT /api/v1/products/{productId}**
상품 수정

**Request**:
- Method: `PUT`
- Endpoint: `/api/v1/products/{productId}`
- Body:
  ```json
  {
    "brandId": 1,
    "categoryId": 1,
    "productNm": "스트라이프 셔츠",
    "price": 70000
  }
  ```

**Response**:
- Status: `200 OK`
- Body:
  ```json
  {
    "id": 74,
    "brandNm": "무신사 스탠다드",
    "CategoryNm": "상의",
    "ProductNm": "스트라이프 셔츠",
    "price": 70000
  }
  ```

### 9. **DELETE /api/v1/products/{productId}**
상품 삭제

**Request**:
- Method: `DELETE`
- Endpoint: `/api/v1/products/{productId}`

**Response**:
- Status: `204 No Content`

---

## Error Handling

---

## 문제 해결 전략
