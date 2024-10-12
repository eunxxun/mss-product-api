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

---

## 문제 해결 전략

- Soft Delete
  - 물리적으로 데이터를 삭제하는 대신 삭제 여부를 사용하여 논리적으로 삭제되었음을 표시합니다.
  - Soft Delete는 데이터 복구가 가능하고 히스토리 추적이 가능합니다. 
  - 물리적으로 삭제하지 않기 때문에 데이터베이스의 저장 공간이 불필요하게 증가 할 수 있기 떄문에 일정 기간이 지난 삭제 데이터를 별도 테이블로 옮겨 최적화 작업을 하여 해결할 수 있습니다.
  - AOP를 사용하여 브랜드가 삭제될 때 관련 상품들도 자동으로 삭제될 수 있게 처리하고 서비스 로직과 해당 로직을 분리했습니다.
- N+1 문제 해결
  - 지연 로딩(Lazy Loading)으로 설정된 경우, 각 Product에 대해 Brand와 Category를 조회하는 추가적인 쿼리가 발생하여 N+1 문제가 생길 수 있습니다.
  - N+1 문제를 해결하기 위해 **fetch join**을 사용하였습니다.
    - Brand와 Category는 Product와의 관계에서 반드시 존재하는 엔티티기 떄문에 fetch join을 선택했습니다.
    - fetch join과 함께 distinct를 사용하여 중복 없이 연관된 필수 엔티티들을 효과적으로 조회할 수 있도록 했습니다.
- 대량의 상품 데이터에 대비한 조회 성능 개선
  - 대량의 상품 데이터를 처리하는 환경에서 조회 성능을 향상 시키기 위해 Redis를 사용했습니다.
    - 로컬캐시가 아닌 글로벌캐시를 사용한 이유는 분산 환경에 대비하기 위해서 입니다.
  - 가격 조회 API 호출시 캐시를 적용하고 상품 생성,수정,삭제시에 캐시를 무효화하여 최신데이터를 유지했습니다.
  - CacheLoggingAspect를 추가하여 캐시가 추가되고 무효화되는 상태를 로깅했습니다.

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
        {"brandNm": "스탠다드", "price": 9000, "categoryNm": "스니커즈"},
        {"brandNm": "스탠다드", "price": 2000, "categoryNm": "가방"},
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
    "brandNm": "스탠다드",
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
    "brandNm": "스탠다드",
    "categoryNm": "상의",
    "productNm": "스트라이프 티셔츠",
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
    "brandNm": "스탠다드",
    "categoryNm": "상의",
    "productNm": "스트라이프 셔츠",
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

