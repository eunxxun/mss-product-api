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