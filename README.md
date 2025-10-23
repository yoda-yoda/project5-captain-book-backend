# 5차 프로젝트 - **대장부(Captain Book) 개인프로젝트 (captain book project)**

## 💡 개인 :대장부(Captain Book) 프로젝트

<img width="350" height="350" alt="DaeJangBu-radius-logo" src="https://github.com/user-attachments/assets/cb7f16af-1c36-4f55-b2e9-54b7f24ff938" />

---

### 리포지터리 링크

* **소개 링크 :** https://github.com/yoda-yoda/project5-captain-book-overview
* **backend (spring) :** https://github.com/yoda-yoda/project5-captain-book-backend
* **frontend (react) :** https://github.com/yoda-yoda/project5-captain-book-frontend

---

<br>

<br><br>

### 🔅 이 프로젝트에서 중점을 둔것
- **(1) 혼자만의 힘** <br>
이 프로젝트의 시작부터 끝까지 모든 과정을 최대한 혼자만의 힘으로 해결하도록 했습니다. <br>
검색과 Ai를 활용할땐 문제를 최대한 제 스스로 이해하며 해결하려 노력했습니다. <br>
왜냐하면 그 과정 자체가 저를 훈련시킬거라 생각했기 때문입니다.

- **(2) 원리 이해 중시** <br>
프로젝트 중 발생한 모든 문제, 궁금증, 해결은 그 원리부터 이해하도록 했습니다. <br>
왜냐하면 이유를 모르고 넘어가는 과정은 명쾌하지않고 불편했기 때문입니다. 따라서 원리를 깊이있게 이해하려 노력했습니다. <br>

- **(3) 다양한 기술 훈련** <br>
가능한 다양한 기술과 까다로운 기술을 접하도록 했습니다. <br>
예를들면 렌더링에선 타임리프와 리액트, 로그인에선 폼로그인과 OAuth, <br>
데이터베이스에선 Redis, H2, MySQL, 시큐리티에선 세션 기반/CSRF 토큰과 JWT 등등 <br>
1가지 기술뿐만 아니라 다양한 기술을 적용해보는 훈련을 했습니다. <br>
리액트에서는 일부러 axios 라이브러리를 사용하지 않고 훈련을 위해 fetch만을 활용하였습니다. <br>
왜냐하면 더 까다로운 기술을 먼저 접하거나 다양한 기술을 경험하면 그 이해와 활용의 폭이 넓어질거라 생각했기 때문입니다. <br><br>

### 💻 메인페이지 
: https://.. (아직은 미배포 상태입니다.) <br><br>

### 📀 진행상태
현재 75% 정도 완성된 상태이며 개발, 문서 업데이트를 진행중입니다. <br><br>


### ⚙️ 현재 프로젝트 복사시 앱 구동에 필요한 환경 설정들

<details>
<summary> 현재 프로젝트 복사시 앱 구동에 필요한 환경 설정들 (클릭) </summary>

<br><br>

**1.** 사용자 컴퓨터에 데이터베이스 MySQL이 (또는 다른 데이터베이스가) 설치되어 있어야함. (Docker로 실행 가능) <br><br>
**2.** 사용자 컴퓨터의 redis가 실행되고 있어야함. (Dokcer로 실행 가능) <br><br>
**3.** Https 설정을 위해 개발용 임시 자체인증서 파일을 스프링의 resources 폴더에 놓기. <br>

<details>
<summary> 개발용 임시 자체인증서 파일 만드는법 (클릭) </summary>

  <br> 컴퓨터에 보통 JAVA가 설치되어 있다면 JDK도 설치되어있다. <br>
JDK 폴더의 'bin' 폴더 안에 **keytool.exe** 이라는 응용프로그램이 있을것이다. <br>
**keytool.exe** 의 위치는 보통 **C:\Program Files\Java\jdk-<버전>\bin** 폴더 내부에 있다. 이것을 활용해 자체인증서 파일을 만들어 스프링에서 활용하는 것이다. <br>
이 exe파일이 컴퓨터에 존재하는지 확인하는 방법 중 하나는, <br>
터미널을 키고 **keytool -version** 명령어를 쳐봤을때 버전이 출력이 되는지 확인하는 것이다. <br>
출력 된다면 해당 'bin' 폴더가 환경 변수(path)로 시스템에 등록되어 있으며 exe 파일이 존재하는 것이고, 이 경우 더 짧은 명령어로 파일을 생성할 수 있다. 아닌 경우의 방법도 하단에 적혀있다.
<br><br>

**아래에서 해당되는 경우의 명령어를 복사하여, 터미널에 입력한다. 그러면 터미널이 열린 폴더에 (springTestKeyStore.p12) 파일이 생성될것이다.**


<br>

### (1) 터미널에서 'keytool -version' 명령어를 쳤을때 출력되는 경우(즉 bin폴더가 환경 변수(path)에 등록되어 있는 경우)

```
keytool -genkeypair -alias springTestKeyStore -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore springTestKeyStore.p12 -validity 3 -storepass 123123 -keypass 123123 -dname "CN=localhost, OU=Dev, O=MyCompany, L=Seoul, ST=Seoul, C=KR"
```
<br>

<details>
<summary> 참고: 해당 명령어의 뜻 살펴보기 (클릭) </summary>

<br><br>

- keytool

역할: Java Development Kit(JDK)에 포함된 기본 유틸리티이다. 디지털 인증서, 키 쌍, 그리고 키 저장소(Keystore)를 관리(생성, 가져오기, 내보내기, 삭제 등)하는 데 사용된다.

- genkeypair

역할: "Generate Key Pair"의 약자로, 새로운 키 쌍(Key Pair)을 생성하라는 주 명령어이다.
원리: 비대칭키 암호화 방식에 따라 한 쌍의 키, 즉 외부에 공개해도 되는 **공개키(Public Key)**와 절대 노출되면 안 되는 **개인키(Private Key)**를 생성한다. 공개키로 암호화한 데이터는 개인키로만 복호화할 수 있으며, 그 반대도 가능하다.
참고: 이 명령을 실행하면 키 쌍과 더불어, 해당 공개키를 포함하는 자체 서명된(self-signed) X.509 인증서가 함께 생성되어 키 저장소에 저장된다.

- alias springTestKeyStore

역할: 생성될 키 쌍(과 인증서)을 키 저장소 내에서 식별하기 위한 **고유한 별칭(이름)**을 지정한다.
원리: 키 저장소는 여러 개의 키와 인증서를 보관할 수 있는 금고와 같다. alias는 그 금고 안의 특정 아이템에 붙이는 이름표이다. 나중에 이 키를 사용하거나 참조할 때 이 별칭(springTestKeyStore)을 사용한다.

- keyalg RSA

역할: "Key Algorithm"의 약자로, 키 쌍을 생성할 때 사용할 암호화 알고리즘을 지정한다.
RSA: Rivest-Shamir-Adleman의 약자로, 현재 가장 널리 사용되는 공개키(비대칭키) 암호화 알고리즘 중 하나이다.

- keysize 2048

역할: 생성할 키의 비트(bit) 단위 크기를 지정한다.
원리: 키의 길이가 길수록 암호화 강도가 높아져 해독하기 어려워진다. 2048비트는 현재 보안상 권장되는 표준적인 키 길이이다. 너무 짧으면 보안에 취약하고, 너무 길면 암호화·복호화 연산 속도가 느려질 수 있다.

- storetype PKCS12

역할: 사용할 **키 저장소(Keystore)의 유형(포맷)**을 지정한다.
PKCS12: 업계 표준 포맷 중 하나로, .p12 또는 .pfx 확장자를 가진다. Java뿐만 아니라 .NET, Python 등 다른 언어·환경과의 호환성이 매우 좋다. Java의 전통적인 기본값인 JKS보다 이 형식을 권장한다.

- keystore springTestKeyStore.p12

역할: 생성된 키 쌍과 인증서를 저장할 파일의 이름(및 경로)을 지정한다.
원리: 이 명령이 실행되면 springTestKeyStore.p12라는 파일이 생성되거나 기존 파일에 추가된다. 이 파일이 바로 키 저장소 파일이다.

- validity 3

역할: 함께 생성되는 자체 서명 인증서의 유효 기간을 일(day) 단위로 지정한다.
여기서 3은 인증서가 3일동안 유효함을 의미한다. 이 기간이 지나면 인증서는 만료되어 신뢰할 수 없는 것으로 간주된다.

- storepass 123123

역할: "Keystore Password"로, 키 저장소 파일(springTestKeyStore.p12)에 접근하기 위한 비밀번호를 설정한다.
원리: 이 키 저장소 파일을 열람하거나 수정하려면 이 비밀번호(123123)가 필요하다. 파일 전체를 보호하는 비밀번호이다.

- keypass 123123

역할: "Key Password"로, 키 저장소 내의 특정 개인키(-alias springTestKeyStore로 지정된)에 접근하기 위한 비밀번호를 설정한다.
원리: 키 저장소 파일(-storepass)을 열었더라도, 그 안의 민감한 개인키를 실제로 사용하려면 이 비밀번호(123123)가 추가로 필요하다. 보안 강화를 위해 -storepass와 다르게 설정할 수도 있지만, 편의상 같게 설정하는 경우도 많다.

- dname "..."

역할: "Distinguished Name(고유 이름)"의 약자로, 생성될 인증서의 소유자 정보를 지정한다.
원리: 이 정보는 인증서가 누구의 것인지를 식별하는 데 사용된다.

CN=localhost: Common Name(일반 이름)이다. 인증서가 사용될 주체(예: 웹사이트 도메인)를 의미하며, localhost로 설정하여 로컬 개발 서버용임을 나타낸다.

OU=Dev: Organizational Unit(조직 단위)이다. 조직 내 부서를 의미한다(예: 개발팀).

O=MyCompany: Organization(조직)이다. 회사 또는 조직 이름을 의미한다.

L=Seoul: Locality(지역)이다. 도시 이름을 의미한다.

ST=Seoul: State or Province(주 또는 시/도)이다.

C=KR: Country(국가)이다. 2자리 국가 코드(대한민국)를 의미한다.
  
</details>

<br><br><br>

이렇게 하면, 터미널이 열린 폴더에 (springTestKeyStore.p12) 파일이 생길것이다. <br>
그 파일을 스프링의 resources 폴더에 놓으면 파일 준비는 끝난것이다.


<br><br><br>

### (2) 터미널에서 'keytool -version' 명령어를 쳤을때 출력되지 않는 경우(즉 bin폴더가 환경 변수(path)에 등록되어 있지 않은 경우)

출력되지 않은 경우는 보통 'bin' 폴더가 환경 변수(path)로 시스템에 등록되지않은 경우이며,
**keytool.exe** 파일이 있는 곳에 직접 들어가서 파일 경로를 확인하면 된다. <br>
대부분 **C:\Program Files\Java\jdk-<버전>\bin** 이러한 경로에 **keytool.exe** 이 존재한다.
(만약 없다면 인터넷에서 'jdk'를 검색해 설치해야할 것이다.)
<br>
그러면 아래 명령어의 '[바꾸기...]' 부분을 그 경로로 변경한 후 명령어를 실행하면 파일이 생길것이다.


### (2-1) 터미널 종류가 파워쉘 인 경우
```
& "[바꾸기. 자신의 keytool.exe가 위치한 경로를 적기. 예시=>C:\Program Files\Java\jdk-21\bin]\keytool.exe" `
  -genkeypair `
  -alias springTestKeyStore `
  -keyalg RSA `
  -keysize 2048 `
  -storetype PKCS12 `
  -keystore springTestKeyStore.p12 `
  -validity 3 `
  -storepass 123123 `
  -keypass 123123 `
  -dname "CN=localhost, OU=Dev, O=MyCompany, L=Seoul, ST=Seoul, C=KR"
```

### (2-2) 터미널 종류가 bash 인 경우
```
"[바꾸기. 자신의 keytool.exe가 위치한 경로를 적기. 예시=>/c/Program Files/Java/jdk-21/bin]/keytool" \
  -genkeypair \
  -alias springTestKeyStore \
  -keyalg RSA \
  -keysize 2048 \
  -storetype PKCS12 \
  -keystore springTestKeyStore.p12 \
  -validity 3 \
  -storepass 123123 \
  -keypass 123123 \
  -dname "CN=localhost, OU=Dev, O=MyCompany, L=Seoul, ST=Seoul, C=KR"
```

이렇게 진행하면, <br>
터미널이 열려있는 폴더에 (springTestKeyStore.p12) 파일이 생길것이다. <br>
그 파일을 스프링의 resources 폴더에 놓으면 파일 준비는 끝난것이다.

</details>

<br><br>

**4.** 밑의 코드를 복사한뒤, 스프링의 프로젝트 resources 폴더에 application.yml 파일 생성 후 붙여넣는다. 이렇게하면 설정파일로 등록된다. <br>

```
server:
  # HTTPS 통신을 위한 설정. (일반적으로 8443 사용)
  port: 8443
  ssl:
    # 만든 key 파일 설정
    key-store: classpath:[바꾸기 자체인증서 파일이름. 예시: springTestKeyStore].p12
    key-store-type: PKCS12
    # keytool 실행 시 입력했던 비밀번호
    key-store-password: [바꾸기 자체인증서 만들때 입력한 비밀번호. 예시: 123123]
    # keytool 실행 시 사용했던 alias
    key-alias: [바꾸기 자체인증서 만들때 입력한 alias. 예시: springTestKeyStore]

spring:

  profiles:
    active: dev

  redis:
    host: localhost # 또는 Redis 서버 주소
    port: 6379


  datasource:

    url: jdbc:mysql://localhost:[바꾸기 mysql이 실행중인 포트번호. 기본값은 3306]/[바꾸기 생성해놓은 데이터베이스 이름]
    driver-class-name: [바꾸기 mysql 드라이버 경로. 기본값은 com.mysql.cj.jdbc.Driver]
    username: [바꾸기 mysql 아이디]
    password: [바꾸기 mysql 비밀번호]


  sql:
    init:
      mode: always

  jpa:
    hibernate:
      ddl-auto: create
      format_sql: true
      use_sql_comments: true
    show-sql: true
    defer-datasource-initialization: true

  security:
    oauth2:
      client:
        registration:
          google:
            client-id: [바꾸기 Google Cloud Console에서 발급받은 구글 OAuth2 client-id]
            client-secret: [바꾸기 Google Cloud Console에서 발급받은 구글 OAuth2 client-secret]
            scope:
              - email
              - profile


  # 로깅확인용
logging:
  level:
    org.springframework.jdbc.datasource.init.ScriptUtils: DEBUG

    org.springframework.security.web.csrf: TRACE
    org.springframework.security.web.authentication: DEBUG
    org.springframework.security.web.access: DEBUG


---
spring:
  config:
    activate:
      on-profile: dev
cors:
  allowed-origins: "https://localhost:3000"

---
spring:
  config:
    activate:
      on-profile: prod

cors:
  allowed-origins: "https://captain-book"
```

<br><br>

**5.** 위 yml 파일의 내용중 "[바꾸기 ...]" 또는 따로 변경할 부분을, 사용자 개인 상황에 맞게 변경한다.

<br>

**6.** (선택) 구글 오아스 로그인 기능을 원할 경우 =>
<br>
사용자가 Google Cloud Console 에서, 구글 OAuth2 'client-id'와 'client-secret'을 발급받아 yml 파일의 해당 부분을 바꿔준다. (안한다면 폼 로그인 기능만 사용 가능)

<br>
<br>

</details>

<br>

---
<br>

### 📜주제

- 웹 가계부 어플리케이션을 구현하였습니다.
- 현재 75% 정도 완성된 상태이며 개발, 문서 업데이트를 진행중입니다. 

<br>

### 🔍 대장부(Captain Book)의 의미  
- **대장부(Captain Book) =>** '대장부'란 단어는 '무언가를 기록하는 큰 장부', '크고 씩씩한 사람'이라는 뜻을 동시에 담고있어 차용하였습니다. <br> '대장부'를 영어로 'Captain Book' 으로 표현했습니다.


<br><br>

---


## 🌟 주요 기능

<br>

### 1. 달력 기능
   - 날짜별로 세부 아이템을 저장할 수 있는 달력 기능 구현
   - 날짜, 제목 설정 가능
   - 달력 저장 및 조회, 수정, 삭제 기능

### 2. 아이템 기능
   - 해당 달력안에서 세부 항목을 기록하는 아이템 기능 구현
   - 아이템 이름, 금액, 종류 설정 가능 
   - 아이템 저장 및 조회, 수정, 삭제 기능

### 3. 폼(Form) 로그인
   - JWT 액세스 토큰 인증 기반, 리프레시 토큰으로 로테이션 전략 구현
   - '직접 가입하기', '직접 로그인' 으로 대장부 웹사이트 전용 계정으로 직접 폼가입 및 폼로그인 가능
   - 폼 회원 userId 중복 불가 처리
   - 로그아웃 기능 (JWT 액세스 토큰, 리프레시 토큰 삭제 기능 구현)

### 4. 오아스(OAuth) 로그인
   - JWT 액세스 토큰 인증 기반, 리프레시 토큰으로 로테이션 전략 구현
   - 소셜 로그인(Google) 기반 OAUTH 2.0 로그인 가능 (Naver, Kakaao는 추후 구현 예정입니다.)
   - 최초 로그인 시 자동 DB 회원가입 처리  
   - 로그아웃 기능 (JWT 액세스 토큰, 리프레시 토큰 삭제 기능 구현)



<br><br>

---

## 🚀**개발 환경**

<br>



- ### 아키텍처

<img width="1792" height="1063" alt="최종아키텍처-captain-book" src="https://github.com/user-attachments/assets/66b3894a-2a79-4755-95f4-1e1a1cd18a32" />

<br><br><br>


- ### **ERD(Spring)**

<img width="1480" height="789" alt="DBerd" src="https://github.com/user-attachments/assets/839bb021-5658-4cfe-887f-910f6b846e42" />


<br><br>



- ### **플로우 차트**

**(전체)**

<img width="525" height="618" alt="flow-전체captain-book" src="https://github.com/user-attachments/assets/81803360-7e2e-4005-8852-c718555beaed" />

<br><br>

**(달력 기능)**
<img width="1350" height="550" alt="flow-달력기능-captain-book" src="https://github.com/user-attachments/assets/78a7d579-04b0-4ac1-93ba-fd6d7edd2644" />

<br><br>

**(아이템 기능)**

<img width="1348" height="552" alt="flow-아이템기능-captain-book" src="https://github.com/user-attachments/assets/ceae2b6d-3d46-4585-9d9f-330563c14e7b" />

<br><br>

**(로그인, 로그아웃 기능)**

<img width="1353" height="447" alt="flow-로그인-로그아웃기능-captain-book" src="https://github.com/user-attachments/assets/2d924c11-6327-4f21-9a45-97c01f902c0d" />

<br><br>

- ### **활용 기술**


<div style="display: flex; flex-wrap: wrap; gap: 10px;">
  <img src="https://img.shields.io/badge/JAVA-a?style=for-the-badge&logo=JAVA&logoSize=auto&color=%23946951">
  <img src="https://img.shields.io/badge/SPRING-a?style=for-the-badge&logo=spring&logoColor=white&logoSize=auto">
  <img src="https://img.shields.io/badge/SECURITY-a?style=for-the-badge&logo=spring%20security&logoColor=white&logoSize=auto&color=%23419e5d">
  <img src="https://img.shields.io/badge/JWT-a?style=for-the-badge&logo=jsonwebtokens&logoColor=white&logoSize=auto&color=%23be48c2">
  <img src="https://img.shields.io/badge/DOCKER-a?style=for-the-badge&logo=docker&logoColor=white&logoSize=auto&color=%232496ED">
  <img src="https://img.shields.io/badge/REDIS-a?style=for-the-badge&logo=redis&logoColor=white&logoSize=auto&color=%23FF4438">
  <img src="https://img.shields.io/badge/MYSQL-a?style=for-the-badge&logo=MYSQL&logoColor=white&logoSize=auto&color=%234479A1">
  <img src="https://img.shields.io/badge/REACT-a?style=for-the-badge&logo=react&logoColor=white&logoSize=auto&color=%2361DAFB">
  <img src="https://img.shields.io/badge/ROUTER-a?style=for-the-badge&logo=reactrouter&logoColor=white&logoSize=auto&color=%23CA4245">
  <img src="https://img.shields.io/badge/BOOTSTRAP-a?style=for-the-badge&logo=bootstrap&logoColor=white&logoSize=auto&color=%237952B3">
  <img src="https://img.shields.io/badge/RECOIL-a?style=for-the-badge&logo=recoil&logoColor=white&logoSize=auto&color=%233578E5">
  <img src="https://img.shields.io/badge/HTML5-a?style=for-the-badge&logo=html5&logoColor=white&logoSize=auto&color=%23E34F26">
  <img src="https://img.shields.io/badge/CSS-a?style=for-the-badge&logo=css&logoColor=white&logoSize=auto&color=%23663399">
  <img src="https://img.shields.io/badge/JAVASCRIPT-a?style=for-the-badge&logo=javascript&logoColor=white&logoSize=auto&color=%23F7DF1E">
  <img src="https://img.shields.io/badge/GIT-a?style=for-the-badge&logo=GIT&logoColor=white&logoSize=auto&color=%23F05032">
  <img src="https://img.shields.io/badge/GITHUB-a?style=for-the-badge&logo=GITHUB&logoColor=white&logoSize=auto&color=%23181717">
</div>


<br>
<br>


<br><br><br>

[▲위로 스크롤](#5차-프로젝트---대장부captain-book-개인프로젝트-captain-book-project)

---

<br>

## 📝 API 명세


<br>

### 인증 관련 API
( AuthController )

| Method | Endpoint                                        | Description                   |
|--------|-------------------------------------------------|-------------------------------|
| **GET**    | `/api/auth/me`      | 정식 사용자 인증을 위한 맵핑포인트이다. 인증이 성공적이면 유저 정보를 응답한다. |
| **GET**    | `/api/auth/ping`    | 시큐리티 필터로 회원 인증 상태만 가볍게 체크할 목적의 맵핑포인트이다. |
| **POST**   | `/api/auth/refresh` | jwt refresh 토큰을 이용해 access 토큰을 재발급하는 엔드포인트이다. |
| **POST**   | `/api/auth/logout`  | 엄격한 로그아웃 처리를 위한 맵핑포인트이다. 리프레시 토큰을 Redis 에서도 삭제하고, 응답으로 브라우저 쿠키에서도 리프레시 토큰을 삭제하도록 만든다. |

---

<br>

### 달력 기능 관련 API
( CalendarController )

| Method | Endpoint                                        | Description                   |
|--------|-------------------------------------------------|-------------------------------|
| **GET**    | `/api/home`          | 해당 회원의 달력 리스트를 조회한다. |
| **GET**    | `/api/calendar/{calendarId}` | 해당 회원의 특정 달력을 조회한다. | 
| **POST**   | `/api/home` | 해당 회원의 특정 달력을 저장한다. |
| **PUT**   | `/api/calendar/update/{calendarId}` | 해당 회원의 특정 달력을 수정한다. |
| **DELETE**   | `/api/calendar/delete/{calendarResponseDtoId}`  | 해당 회원의 특정 달력을 삭제한다. |

---

<br>

### 아이템 기능 관련 API
( CalendarItemController )

| Method | Endpoint                                        | Description                   |
|--------|-------------------------------------------------|-------------------------------|
| **GET**    | `/api/calendar/{calendarId}/item`      | 해당 회원 특정 달력의 아이템 리스트를 조회한다. |
| **GET**    | `/api/calendar/{calendarId}/item/{calendarItemId}`    | 해당 회원 특정 달력의 특정 아이템을 조회한다. | 
| **POST**   | `/api/calendar/{calendarId}/item` | 해당 회원 특정 달력의 특정 아이템을 저장한다. |
| **PUT**   | `/api/calendar/item/{calendarItemId}/update`  | 해당 회원 특정 달력의 특정 아이템을 수정한다. |
| **DELETE**   | `/api/calendar/item/{calendarItemId}/delete`  | 해당 회원 특정 달력의 특정 아이템을 삭제한다. |

---


<br>

### 사용자(멤버) 기능 관련 API
( MemberController )

| Method | Endpoint                                        | Description                   |
|--------|-------------------------------------------------|-------------------------------|
| **GET**    | `/api/members/exits`      |  폼 회원 가입시 중복 아이디가 있는지 체크하는 맵핑포인트이다. |
| **GET**    | `/api/members`    | DB에 저장된 모든 회원 리스트를 조회(응답)한다. |    
| **GET**    | `/api/members/{memberId}`    | DB에 저장된 특정 회원을 조회(응답)한다. |    
| **POST**   | `/api/members` | 폼 회원 가입 맵핑포인트이다. DB에 특정 폼 회원 정보를 저장한다. |
| **PUT**   | `/api/members/update/{memberId}`  | 특정 회원의 정보를 수정한다. |
| **DELETE**   | `/api/members/delete/{memberResponseDtoId}`  | 특정 회원의 정보를 삭제한다. |

---

[▲위로 스크롤](#5차-프로젝트---대장부captain-book-개인프로젝트-captain-book-project)

<br><br><br><br><br>
