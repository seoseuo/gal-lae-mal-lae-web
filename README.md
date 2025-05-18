<img src="https://github.com/user-attachments/assets/04d58dd4-1556-4bfa-b158-355bed77e5c8" style="width: 250px; height: 250px;" />

### 갈래말래
> 갈래말래, 여행 모임 일정 관리 및 장소 추천 커뮤니티 웹 서비스

<a href="https://chivalrous-saffron-326.notion.site/1d10ba93975b8086a976d70dd9224326?pvs=4"><img src="https://img.shields.io/badge/Notion 링크 보기 -E6E6E6?style=for-the-badge&logo=notion&logoColor=black" /></a>
<a href="https://chivalrous-saffron-326.notion.site/1f70ba93975b80489b3dc3e98f0bf2b2?pvs=4"><img src="https://img.shields.io/badge/갈래말래 산출물 -E6E6E6?style=for-the-badge&logo=notion&logoColor=black" /></a>
<br><a href="https://velog.io/@seuo/series/%EA%B0%88%EB%9E%98%EB%A7%90%EB%9E%98" target="_blank" rel="noopener noreferrer">
  <img src="https://img.shields.io/badge/[시리즈 | 갈래말래 : velog]-20C997?style=for-the-badge&logo=velog&logoColor=black&labelColor=20C997&color=20C997" />
</a>
<hr>

# 개요

![image](https://github.com/user-attachments/assets/fd77f06d-4228-48c4-91ed-ef48591f0280)


![image](https://github.com/user-attachments/assets/63569d05-3bd1-412c-99e2-303af34c98ac)


# 주요 기능

## JWT 토큰

![image](https://github.com/user-attachments/assets/b34fff96-ec97-40f1-8428-c0191e069c1f)


> JWT 토큰 기반 인증 방식 도입으로 사용자 인증 및 보안 강화
> 

## 모임 여행 생성 및 관리

![image](https://github.com/user-attachments/assets/653abd88-27f0-4729-8218-cee7888174b3)


> 모임 여행 생성 시, 각 도/시 단계별 데이터를 Redis에 순차적으로 저장 처리
> 

# 기술

## 스택

![image](https://github.com/user-attachments/assets/8a5d083d-f686-4fbc-83a7-f3e19a16f095)


## 아키텍처

### 시스템

![image](https://github.com/user-attachments/assets/0e2bdb27-7e75-483b-9651-e305ae782395)


> AWS EC2 프론트엔드와 백엔드를 Docker 컨테이너로 분리 배포하고, Nginx를 통해 통합 관리
> 

### API 서버

![image](https://github.com/user-attachments/assets/5d274ab2-c768-42a0-a84d-586ca3d0c507)


> JPA 기반 Entity-Mapper-Repository 설계를 활용하여 도메인 중심 데이터 처리 구현
Spring Boot 기반 모듈화 된 설계로 확장성과 유지 보수성을 강화한 웹 서비스 제작
> 

### Spring Security

![image](https://github.com/user-attachments/assets/b55c3b74-6c79-46f3-be23-42ea025d1443)


> Spring Security 기반 역할 권한 설정으로 민감 정보 보호 및 접근 제어 구현
프론트엔드와 백엔드 분리 운영에 따른 CORS 정책 설정 및 도메인 허용 처리
> 

### PWA

![image](https://github.com/user-attachments/assets/d9093451-b031-4f43-9b70-22840b976485)


> 웹 어플리케이션을 PWA(Progressive Web App)로 변환하여 모바일 앱 환경 타겟팅
> 

## DevOps

![image](https://github.com/user-attachments/assets/6e69f267-8654-4ec6-99e9-6bb00cdf8c0a)


> Git Flow 전략(Main, Develop 브랜치 기반)을 따르며 체계적인 버전 관리 및 협업 환경 유지
> 

**FE**

![image](https://github.com/user-attachments/assets/355003ac-2b06-4bbe-8407-505ef952ccda)


**BE**

![image](https://github.com/user-attachments/assets/95230249-0e9f-4749-bbe8-02faffff2ce4)


> GitHub Action 기반 CI/CD 파이프라인 구축으로 코드 Push 시 Docker 이미지 빌드 및 EC2 배포 자동화
> 

| 약어  | 이름                               | 뜻                                                                 |
|-------|------------------------------------|----------------------------------------------------------------------|
| **CI** | **C**ontinuous **I**ntegration     | **지속적 통합**: 개발자들이 변경한 코드를 자주(매일 여러 번) **자동으로 빌드하고 테스트**하는 것 |
| **CD** | **C**ontinuous **D**elivery 또는 **C**ontinuous **D**eployment | **지속적 전달** 또는 **지속적 배포**: CI 이후, **자동으로 배포 가능한 상태**로 만들거나 실제로 **자동 배포**까지 하는 것<br>• Docker를 활용한 백엔드 서비스 컨테이너화로 환경 일관성 및 배포 효율성 확보 |


## 테스트 및 검증 도구

### Postman

![image](https://github.com/user-attachments/assets/6bb7ee63-0567-4054-84ad-34397897f077)


> API 서버 제작 과정 시 Postman을 이용한 API 테스트 및 검증으로 개발 단계에서의 오류 최소화 추구

<hr>

### 작업 전 공부 내용

<details>
<summary>TDD (Test - Driven - Development)</summary>

> 코드의 양질을 위해 테스트를 먼저 작성하고 그 테스트를 통과하는 최소한의 코드를 작성한 후, 리팩토링을 통해 개선하는 개발 방식이다.

### 핵심 개념
1. `Red` (실패 시)
- 기능이 구현되지 않았으므로 코드는 당연히 실패한다.
2. `Green` (성공 시, 최소한의 코드 작성)
- 통과할 수 있도록 최소한의 코드만 작성한다.
- 최소한만 구현하는 것이 목적이다.
3. `Refactor` (리팩토링)
- 중복 코드 제거 및 성능 개선, 코드 가독성을 높이기 위해 리팩토링을 진행한다.

과정이 반복되면서 점진적으로 안정적인 코드와 유지보수하기 수월한 구조를 만들 수 있다.
</details>

<details>
<summary>단위 테스트와 통합 테스트</summary>

### 단위 테스트 (Unit Test)
> 프로그램의 개별 단위를 독립적으로 테스트하는 과정이다.
- 하나의 함수, 메서드, 클래스 등 최소 단위만 테스트한다.
- 외부 시스템 (DB,API 등) 과 연결되지 않은 상태에서 실행한다.
- 빠른 실행속도를 가진다.

### 통합 테스트 (Integration Test)
> 여러 개의 모듈 또는 시스템이 함께 동작하는지 테스트하는 과정
- 단위 테스트와 달리 서로 다른 모듈 (클래스, DB, API) 간의 상호 작용을 확인한다.
- 실제 환경과 유사한 상태에서 테스트한다.
JPA 환경에서의 Test 클래스 작성
- 실행 속도가 느릴 수 있다.
</details>

<details>
<summary>JUnit</summary>

> JUnit은 Java에서 단위 테스트를 수행하기 위한 대표적인 테스트 프레임워크이다, 테스트를 자동화하고 코드의 안정성을 높이는 역할을 한다.
- Java 기반의 단위 테스트 프레임워크
- 간단한 어노테이션 (`@Test` , `@BeforeEach` 등) 을 사용하여 테스트 작성이 가능하다.
- 테스트 코드 실행 결과를 자동으로 확인한다.
- Spring Boot, Maven, Gradle 등과 쉽게 연동이 가능하다.

| 어노테이션           | 설명                                 |
|---------------------|----------------------------------|
| `@Test`            | 테스트 메서드를 나타냄             |
| `@BeforeEach`      | 각 테스트 실행 전에 실행           |
| `@AfterEach`       | 각 테스트 실행 후에 실행           |
| `@BeforeAll`       | 모든 테스트 전에 한 번 실행 (static) |
| `@AfterAll`        | 모든 테스트 후에 한 번 실행 (static) |
| `@Disabled`        | 테스트를 실행하지 않음 (비활성화)   |
| `@DisplayName`     | 테스트 이름을 지정                |
| `@ParameterizedTest` | 매개변수를 사용한 테스트        |
</details>

<details>
<summary>JPA 환경에서의 통합 테스트</summary>

`@SpringBootTest` : JPA 환경에서 스프링 컨텍스트를 로드하여 테스트 실행<br>
`@Transactional` : 테스트 종료 후 데이터 롤백

* 스프링 컨텍스트를 로드하여 테스트 실행
> Spring Context 는 애플리케이션에서 사용할 객체(빈, Bean)를 관리하는 컨테이너이다. 즉, 스프링이 필요한 객체를 생성하고 의존성을 주입하고 생명주기를 관리하는 환경을 말한다. @SpringBootTest 어노테이션을 사용하면, 스프링이 애플리케이션의 모든 설정을 로드하고 필요한 객체를 생성하여 테스트 환경을 준비한다.

## usIdx 를 이용해 회원 정보를 가져오는 findByUsIdx 메서드 테스트
### UserService 클래스

```java
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserDTO findByUsIdx(int usIdx) {
        Optional<User> userOptional = userRepository.findByUsIdx(usIdx);        
        return userOptional.map(userMapper::toDTO).orElse(null);
    }
}
