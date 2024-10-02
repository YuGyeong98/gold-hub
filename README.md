# GoldHub<img src="https://em-content.zobj.net/source/apple/391/coin_1fa99.png" align=left width=100>

> 금 구매, 판매 주문을 관리하는 서비스

## ✨ 서비스 개요

1. 회원가입, 로그인 인증
2. 인증을 통해 금 구매, 판매 주문
3. 인증을 담당하는 인증 서버(auth server)와 구매, 판매 주문을 수행하는 자원 서버(resource server) 분리
4. gRPC를 통해 인증 서버와 자원 서버 간 통신
<br/>

## 📚 사용 스택

<div>
<img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white">
</div>

<div>
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat-square&logo=Spring Boot&logoColor=white">
<img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=Gradle&logoColor=white">
</div>

<div>
<img src="https://img.shields.io/badge/MariaDB-003545.svg?style=flat-square&logo=MariaDB&logoColor=white">
<img src="https://img.shields.io/badge/Spring Data JPA-6DB33F.svg?style=flat-square&logo=Spring Data JPA&logoColor=white">
<img src="https://img.shields.io/badge/Querydsl-0085CA.svg?style=flat-square&logo=Querydsl&logoColor=white">
</div>

<div>
<img src="https://img.shields.io/badge/gRPC-00878F?style=flat-square&logo=gRPC&logoColor=white">
</div>

<div>
<img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=Docker&logoColor=white">
<img src="https://img.shields.io/badge/JSON Web Tokens-000000?style=flat-square&logo=JSON Web Tokens&logoColor=white">
</div>

<div>
<img src="https://img.shields.io/badge/Swagger-85EA2D?style=flat-square&logo=Swagger&logoColor=black">
</div>

## 💾 ERD

![ERD](https://github.com/user-attachments/assets/b4f31d0c-04a7-4c89-83f0-d8065e0619eb)

### 1. user (사용자)

- 서비스에 등록한 사용자
- 비밀번호는 암호화하여 저장

### 2. product (상품)

- 서비스에서 구매, 판매할 수 있는 상품
- orders(주문) 테이블과 일대다 관계

### 3. orders (주문)

- 상품 주문 내역
- 주문 식별자(order_id) 외에 관리자가 쉽게 관리할 수 있도록 주문 번호(order_number) 컬럼 추가
  </br>
  ex) `ORDER-20241002-00001`
- 수량(quantity) 단위는 그램(g)이며, 정확성과 신뢰성을 보장하기 위해 `DECIMAL` 타입을 사용하여 최대 정수 8자리, 소수 2자리까지 저장

## 📁 Project Structure

- Layered Architecture를 적용했습니다.
- 각 계층에서는 자신의 책임 외에 하위 계층에만 의존적인 구조를 적용하여, 응집도를 높이고 결합도를 낮췄습니다.

```bash
├── server # 인증 서버, 자원 서버
│   ├── common
│   │   ├── config # gRPC 서버, JWT 등 각종 설정
│   │   ├── exception # 에러 코드 등 공통 예외처리
│   │   ├── model # 공통으로 쓰이는 엔티티
│   ├── <도메인> # ex) user, order, product
│   │   ├── controller # 컨트롤러
│   │   ├── domain # 도메인
│   │   ├── exception # 도메인별 에러 정의
│   │   ├── repository # 레포지토리
│   │   └── service # 서비스
├── proto # gRPC 통신에 필요한 proto 파일
```

## 🌠 API 명세서

- 로컬 서버 구동
  후 [Auth Server Swagger](http://localhost:8888/swagger-ui/index.html), [Resource Server Swagger](http://localhost:9999/swagger-ui/index.html)
- [Postman](https://documenter.getpostman.com/view/15632236/2sAXxJiuv9)

## 💻 Developer

|                                                                   **김유경**                                                                    |
|:--------------------------------------------------------------------------------------------------------------------------------------------:|
| [<img src="https://avatars.githubusercontent.com/u/58517873?v=4" height=100 width=100><br/><I>YuGyeong98</I>](https://github.com/YuGyeong98) |