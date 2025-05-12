# hello-search-api

ElasticSearch 기반의 E-commerce 상품 검색 API

## 개요

**hello-search-api**는 ElasticSearch를 활용하여 다양한 조건의 상품 검색 기능을 제공하는 API 서버입니다.  
전자상거래(E-commerce) 환경을 모델링하여, 실질적인 검색 로직을 구성하는 데 중점을 두었습니다.

## 기술 스택

- Java 17
- Spring Boot 3.x
- ElasticSearch 8.x
- Gradle 8.x

## 프로젝트 구조
```
hello-search-api/
├── api/               # 검색 API Controller
├── config/            # ElasticSearch 클라이언트 설정
├── domain/            # 엔티티 및 인덱스 모델
├── dto/               # 요청/응답 DTO
├── repository/        # ElasticSearch 쿼리 수행 레이어
├── service/           # 검색 비즈니스 로직
└── HelloSearchApiApplication.java
```
## 주요 기능

- 상품명 키워드 검색
- 가격 범위(minPrice, maxPrice) 필터링
- 정렬 옵션 지원 (가격 오름차순/내림차순)
- ElasticSearch `RestHighLevelClient` 직접 사용
- 단일 인덱스(`product`) 기반 검색

## 실행 방법

1. ElasticSearch 8.x 이상 서버를 실행합니다. (로컬 또는 클라우드)
2. 프로젝트를 클론합니다.

    ```bash
    git clone https://github.com/seunggulee1007/hello-search-api.git
    ```

3. ElasticSearch 연결 설정을 확인합니다.  
   (`ElasticSearchConfig.java`에서 호스트 및 포트 수정 가능)

4. 프로젝트를 빌드 및 실행합니다.

    ```bash
    ./gradlew bootRun
    ```

5. 검색 API 호출 예시

    ```http
    POST /api/v1/search
    Content-Type: application/json

    {
      "keyword": "노트북",
      "minPrice": 1000000,
      "maxPrice": 3000000,
      "sort": "price_asc"
    }
    ```

## API 스펙

| Method | URL            | Description            |
|--------|----------------|------------------------|
| POST   | `/api/v1/search` | 검색 조건에 따른 상품 조회 |

### 요청 파라미터

| 필드명     | 타입    | 설명                   |
|------------|---------|------------------------|
| `keyword`  | String  | 검색할 키워드 (optional) |
| `minPrice` | Integer | 최소 가격 (optional)     |
| `maxPrice` | Integer | 최대 가격 (optional)     |
| `sort`     | String  | 정렬 조건 (`price_asc`, `price_desc`) (optional) |

### 응답 예시

```json
{
  "products": [
    {
      "productId": "1",
      "productName": "Apple MacBook Pro 14",
      "price": 2700000,
      "category": "노트북"
    },
    {
      "productId": "2",
      "productName": "LG Gram 16",
      "price": 2200000,
      "category": "노트북"
    }
  ]
}
```
## 주의사항
	•	ElasticSearch 서버가 사전에 기동되어 있어야 합니다.
	•	현재 인증/보안 처리가 없는 기본 클러스터 연결을 가정합니다.
	•	인덱스명은 기본적으로 product로 고정되어 있습니다.

## TODO
	•	검색 결과 페이징 처리 추가
	•	에러 처리 표준화
	•	ElasticSearch Query DSL 고도화
	•	Docker 환경 구성 추가 (ElasticSearch + API 서버)
