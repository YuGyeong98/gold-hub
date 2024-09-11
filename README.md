# 🪙 GoldHub

## Quick Start

1. 애플리케이션 DB 실행

```
docker compose up -d
```

2. 애플리케이션 실행

- 인텔리제이에서 `AuthServerApplication`, `ResourceServerApplication`을 각각 실행합니다.

## ERD

[DB Diagram](https://dbdiagram.io/d/66e1a3706dde7f4149c7f9df)

## API 명세서

### Swagger

1. Auth Server - http://localhost:8888/swagger-ui.html

- 회원가입 `POST /api/v1/users/register`
- 로그인 `POST /api/v1/users/login`

2. Resource Server - http://localhost:9999/swagger-ui.html

- 주문 생성 `POST /api/v1/orders`
- 주문 조회 `GET /api/v1/orders`
- 주문 상태 업데이트 `PATCH /api/v1/orders/{orderId}`
- 인증 서버를 통해 JWT를 검증합니다.