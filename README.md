# 자취생을 부탁해

skills: CSS, HTML, Java, JavaScript, Oracle

## 📑 소개

### 요리 레시피 공유 웹사이트

요리를 자유자재로 하지 못하는 사람들은 요리를 하기 위해 레시피를 찾아봐야 하고 이후 그 레시피를 다시 참고하기위해서는 다시 그 레시피를 찾기 위해 검색을 해야합니다. 또한 자주 참고하는 레시피를 모아둘 수 없습니다. 

따라서 이러한 불편함을 해소하고자 만든 웹사이트 입니다.


## 👩‍👩‍👦‍👦 기간 / 인원

---

### 기간

2023.10.10 ~ 2023.10.21

## FrameWork / Library

- Spring Boot
- Spring Security
- Thymleaf
- stomp
- JWT
- JPA

## Tools

- InteliJ
- Aquery

## 협업

- Git

### 인원

3명

## Language / Server / Deployment

- Java
- HTML + CSS + JS
- Tomcat 9.0

## DB

- Oracle

---

## 📚 ERD

![Untitled](https://github.com/gyes2/jache/assets/121879651/d80727a7-5949-4110-83fd-0fa159ddb59f)
---

## 📜 핵심 기능

<aside>
⚪ **레시피 등록 기능**

사용자가 자신의 레시피를 동적으로 요리 재료와 요리 순서를 사진과 함께 등록할 수 있는 기능

</aside>

<aside>
⚪ **카테고리별 레시피 조회 기능**

레시피 등록 시 설정한 카테고리를 따라 카테고리별로 레시피를 조회할 수 있는 기능

</aside>

<aside>
⚪ **좋아요 기능**

해당 레시피에 좋아요를 누르게 되면 유저의 좋아요 레시피 화면에서 모아볼 수 있는 기능

</aside>

<aside>
⚪ **채팅 기능**

해당 채팅방에 들어가 여러 사람들이 채팅을 할 수 있는 기능

</aside>

---

## 🖥 개발 내용

SpringBoot 를 이용한 **MVC 패턴**의 RESTFul API 생성

## 👤지원자 기여

### 역할: 팀장

### 맡은 기능

<aside>
⚪ **Backend 총괄 및 개발**

- 프로젝트 기획 및 스토리 보드 작성
- ERD 설계
- 이메일 인증 기능
- 프로젝트 총괄
- 팀원들의 API 연동 도움
- 실시간 채팅 기능 구현
- API 개발
    
![Untitled 1](https://github.com/gyes2/jache/assets/121879651/e9f57be4-f5e7-4b51-9137-e77df1d11899)
    
</aside>

<aside>
⚪ **이메일 인증 기능**

- 구글 smtp 사용
- 회원 가입 시 인증 번호를 생성하여 사용자가 입력한 메일로 전송
- 해당 인증 번호와 사용자가 입력한 인증 번호가 일치해야 회원 가입 가능
</aside>

---

## 💡 성장 경험

<aside>
🔥 **Spring Security 적용을 통한 접근 권한 제공 및 제어와 JWT 토큰**

spring security를 통해 해당하는 api를 요청하는 클라이언트의 권한을 확인하여 권한이 있으면 그 api 의 결과를 response해주고 권한이 없다면 권한이 없다라는 response를 줄 수 있게끔 제어를 하는 방법을 알게 되었습니다.
JWT 토큰을 활용하여 인증/인가 서비스 구현 역량도 키울 수 있었습니다.

</aside>

<aside>
🔥 **RESTFul API**

api를 구현하며 가장 고민이 됐던 부분이 레시피 작성 시 재료와 요리 순서의 동적 추가였습니다. DB에는 레시피의 id를 통해 FK로 참조를 하고 있었기 때문에 어떻게 api를 구성해야 할지에 대해 고민하였습니다. 따라서 레시피 작성을 클릭하게 되면 레시피를 생성하여 주고 생성된 레시피 ID를 프론트에게 넘겨주게 되면 해당 ID를 통해 요리 재료와 요리 순서를 동적으로 추가하는 api를 통해 등록합니다. 또한 레시피 작성을 완료했다는 버튼을 클릭하면 PATCH를 통해 해당 레시피의 데이터를 수정하여 주면 어떨까라는 생각을 하게 되어 적용하였습니다. 

또한 기본적인 CRUD를 하는 API도 많이 구현을 하게 되면서 **RESTFul API에 대한 이해도가 높아진 것** 같습니다.

</aside>

<aside>
🔥 **ERD 설계**

해당 웹서비스를 기획하고 스토리 보드와 요구사항을 보며 ERD 설계를 직접 해보았습니다. 

ERD 설계 시 어떻게 해야 후에 API 설계 시 확장성과 유지 보수를 용이할 수 있게 할까를 초점에 두고 하였습니다.

처음 공부를 할 때에는 ERD 설계에 어려움을 느꼈지만 이번 프로젝트까지 여러 번 설계를 하게 되며 어떻게 해야 확장성과 유지보수를 용이하게 할 지에 대해 조금은 알게 된 것 같습니다.
추후에는 인덱스를 적용하여 검색의 성능을 높이고 싶습니다.

</aside>

<aside>
🔥 **stomp를 통한 실시간 채팅**

스프링 부트에서 채팅을 하기 위해 사용할 수 있는 라이브러리인 stomp를 통해 채팅을 완벽히 구현해보지는 않았지만 실시간으로 채팅을 해보고 그 채팅 내용을 담을 수 있는 데이터베이스를 만들어 그곳에다가 채팅 내역들을 불러올 수 있게 구현했던 점이 기존에 채팅 구현 시 실패했던 db를 극복하였습니다.

</aside>

 

---

## 👀 서비스 화면

메인 화면

![Untitled 2](https://github.com/gyes2/jache/assets/121879651/545e49e1-b4dc-420d-985c-1c088e622995)

로그인 전

![Untitled 3](https://github.com/gyes2/jache/assets/121879651/adc9ba3b-b3f5-49ea-a2b7-e754a966f227)

로그인 후

로그인 및 회원 가입 화면

![Untitled 4](https://github.com/gyes2/jache/assets/121879651/1516deb3-a47f-4781-9cf8-ea78ce32e82e)

유저가 작성한 레시피 목록 화면

![Untitled 5](https://github.com/gyes2/jache/assets/121879651/0150fad7-aea8-437e-9d4c-63151af15e0a)

좋아요한 레시피 모아보는 화면

![Untitled 6](https://github.com/gyes2/jache/assets/121879651/a982337f-313d-4cbc-9605-b3f0bb1e8776)

레시피 작성 화면

![Untitled 7](https://github.com/gyes2/jache/assets/121879651/5eca8433-4f99-4592-b2aa-b2a385f4390c)
