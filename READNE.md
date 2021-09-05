# 카카오페이 부동산/신용 투자 서비스 API

---
카카오페이 부동산/신용 투자 서비스 구현하기

## 구현 기술 목록

---
+ 웹서버 : Spring Boot
+ DBMS : MySql
+ 빌드툴 : Gradle

## DBMS

---

{DB스키마 이미지 올리기}

TB_PRDT_MST - 투자상품마스터

TB_CUDT_MST - 투자고객마스터

## API 목록

---
### 1. 전체투자상품조회 API

기능 : 투자 가능 상품을 가져온다.

Request
+ URL : http://localhost:8080/selPrdtInfo
+ Method : POST

Response
```json
{
    "prdtList": [
        {
            "prdtStatCd": [모집상태],
            "prdtNm": [상품명],
            "curUser": [현재투자자수],
            "prdtId": [상품ID],
            "startedAt": [투자시작일시],
            "totAmt": [총모집금액],
            "curAmt": [현재모집금액],
            "finishedAt": [투자만료일시]
        },
        ...
    ],
    "respMsg": [응답메시지],
    "respCd": [응답코드]
}
```
Response(example)
```json
{
    "prdtList": [
        {
            "prdtStat": "모집중",
            "prdtNm": "상품01",
            "curUser": 5,
            "prdtId": "PRDT0001",
            "startedAt": "2020-09-04 13:11:44",
            "totAmt": 100000000,
            "curAmt": 5000,
            "finishedAt": "2022-09-04 02:11:49"
        },
        {
            "prdtStat": "모집완료",
            "prdtNm": "상품02",
            "curUser": 2,
            "prdtId": "PRDT0002",
            "startedAt": "2020-09-04 02:11:44",
            "totAmt": 200000000,
            "curAmt": 200000000,
            "finishedAt": "2022-09-04 02:11:49"
        }
    ],
    "respMsg": "정상완료",
    "respCd": "000"
}
```
---
### 2. 투자하기 API

기능 : 

Request
+ URL : http://localhost:8080/buyPrdt
+ Method : POST
+ Header
    + X-USER-ID : [사용자 식별값]
```json
{
    "prdtId": [상품ID],
    "ivstAmt": [투자금액]
}
```

Response
```json
{
  "respMsg": [응답메시지],
  "respCd": [응답코드]
}
```

Request(example)
+ Header
    + X-USER-ID : 10001
```json
{
    "prdtId": "PRDT0001",
    "ivstAmt": 1000000
}
```

Response(example)
```json
{
  "respMsg": "정상완료",
  "respCd": "000"
}
```
---
### 3. 나의투자상품조회 API

기능 :
Request
Response
