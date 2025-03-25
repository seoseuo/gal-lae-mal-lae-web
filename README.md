# TDD (Test - Driven - Development)
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



# 단위 테스트와 통합 테스트
### 단위 테스트 (Unit Test)
> 프로그램의 개별 단위를 독립적으로 테스트하는 과정이다.
- 하나의 함수, 메서드, 클래스 등 최소 단위만 테스트한다.
- 외부 시스템 (DB,API 등) 과 연결되지 않은 상태에서 실행한다.
- 빠른 실행속도를 가진다.

### 통합 테스트 (Integration Test)
> 여러 개의 모듈 또는 시스템이 함께 동작하는지 테스트하는 과정
- 단위 테스트와 달리 서로 다른 모듈 (클래스, DB, API) 간의 상호 작용을 확인한다.
- 실제 환경과 유사한 상테에서 테스트한다.
JPA 환경에서의 Test 클래스 작성
- 실행 속도가 느릴 수 있다.

# JUnit
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


# JPA 환경에서의 통합 테스트
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
```

### UserServiceTests 클래스

```java
@Log4j2
@DisplayName("내 정보 열람 테스트")
@SpringBootTest
@Transactional
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Test
    void testGetMyInfo() {
        Optional<User> userOptional = userRepository.findByUsIdx(1);
        UserDTO userDTO = userOptional.map(userMapper::toDTO).orElse(null);
        log.info("findByUsIdx : {}", userDTO);
    }
}
```

---

# JWT 토큰 (Json Web Token)
> 유저를 인증하고 식별하기 위한 토큰(Token) 기반 인증이다. base64로 인코딩 한다.
- 토큰 자체에 사용자의 권한 정보나 서비스를 사용하기 위한 정보가 포함된다.
- RESTful 과 같은 무상태(Stateless)인 환경에서 사용자 데이터를 주고받을 수 있게 된다.
- 토큰을 클라이언트에 저장하고 요청 시 HTTP 헤더에 토큰을 첨부하는 것만으로도 단순하게 데이터를 요청하고 응답받는다.

1. `헤더(Header)` : 토큰의 타입과 서명 알고리즘 정보를 포함한다.
2. `페이로드(Payload)` : 사용자 정보와 권한 정보가 포함된다.
3. `서명(Signature)` : 헤더와 페이로드를 비밀키로 서명한 값으로, 토큰의 무결성을 검증하는 데 사용한다.

```
헤더: {"alg": "HS256", "typ": "JWT"}
페이로드: {"sub": "1234567890", "name": "John Doe", "role": "admin"}
서명: (헤더 + 페이로드를 비밀키로 서명한 값)
```
> role은 배열의 형태로 여러가지를 저장할 수 있다.

## 클레임 (Claims)
> 각 하나의 덩어리를 클레임 이라고 칭한다.
#### 1. 표준 클레임 (Registered Claims)
> JWT에는 이미 정의된 표준 클레임들이 있으며, 이는 특정 용도로 사용되도록 설계된 클레임들이다.
- `iss` (Issuer): 토큰을 발급한 서버의 식별자
- `sub` (Subject): 토큰의 주체, 예를 들어 사용자 ID
- `aud` (Audience): 토큰을 사용할 대상 (예: API 서버)
- `exp` (Expiration Time): 토큰 만료 시간
- `nbf` (Not Before): 토큰이 유효해지는 시간
- `iat` (Issued At): 토큰이 발급된 시간
- `jti` (JWT ID): 토큰의 고유 식별자

#### 2. 비표준 클레임 (Custom Claims)
> 사용자가 필요에 따라 임의로 정의할 수 있다.
</details>

## JWT 의 흐름
### 1. 클라이언트에서 JWT 발급 받기 (로그인 시)
#### 1.1 로그인 요청 (클라이언트 -> 백엔드)
> 클라이언트에서 로그인 폼을 제출할 때, 사용자로 부터 아이디와 비밀번호를 받아 백엔드 서버에 로그인 요청을 보낸다.
```javascript
const login = async (username, password) => {
  const response = await fetch('https://example.com/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ username, password }),
  });

  const data = await response.json();
  return data.token; // 서버로부터 JWT를 반환받음
};

```

#### 1.2 로그인 인증 및 JWT 발급 (백엔드)
> 백엔드에서는 요청을 받으면, 아이디와 비밀번호를 확인하고, 사용자가 유효한지 체크한 후, JWT를 생성하여 클라이언트에 반환한다.
- 서버에서 JWT를 생성하는 과정은 유저 정보를 `페이로드`에 담고 `서명`으로 서명한 후 토큰을 생성한다.
```java
public String createJwtToken(UserDetails userDetails) {
    Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
    claims.put("roles", userDetails.getAuthorities());

    return Jwts.builder()
               .setClaims(claims)
               .setIssuedAt(new Date())
               .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 만료시간
               .signWith(SignatureAlgorithm.HS256, "secretkey") // 서명
               .compact();
}
```

#### 1.3 JWT반환 (백엔드 -> 클라이언트)
> 클라이언트는 JWT를 로컬 스토리지나 세션 스토리지에 저장하낟. 이때 로컬 스토리지는 페이지를 새로 고침하더라도 토큰이 유지되지만, 보안상 세션 스토리지를 사용하는 경우도 있다.

### 2. JWT 저장 (클라이언트)
> JWT를 클라이언트는 로컬 스토리지나 세션 스토리지에 저장한다.
``` javascript
localStorage.setItem('jwt', token); // 로그인 시 받은 JWT 저장
```
- 로컬 스토리지는 페이지를 새로고침하거나 브라우저를 종료하더라도 데이터가 유지된다.
- 세션 스토리지는 탭이 닫히면 데이터가 사라지지만, 탭을 새로고침해도 데이터를 유지한다.

### 3. 클라이언트에서 JWT를 이용해 요청 보내기
> 클라이언트는 서버에 요청을 보낼 때 JWT를 HTTP 헤더에 포함시켜 보낸다. 일반적으로 `Authoreization` 헤더를 사용하여 토큰을 전송한다.

```javascript
const getData = async () => {
  const token = localStorage.getItem('jwt'); // 로컬 스토리지에서 JWT 가져오기

  const response = await fetch('https://example.com/protected-resource', {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${token}`, // JWT를 Authorization 헤더에 포함
    },
  });

  const data = await response.json();
  return data;
};
```

### 4. 백엔드에서 JWT 검증 및 권한 확인
#### 4.1 JWT 검증
> 백엔드에서는 요청이 오면, `Authorization` 헤더에서 JWT를 추출하고 서명 확인 및 만료 시간 확인을 통해 JWT가 유효한지 확인한다.
-  컨트롤러에서 매 요청마다 `@RequestHeader("Authorization")`를 사용하여 클라이언트로부터 Authorization 헤더를 받아야 한다.
``` java
public Claims validateJwtToken(String token) {
    try {
        return Jwts.parser()
                   .setSigningKey("secretkey") // 서버의 비밀 키
                   .parseClaimsJws(token) // 토큰 파싱
                   .getBody(); // payload 반환
    } catch (JwtException e) {
        // 토큰이 만료되었거나, 서명이 맞지 않으면 예외 처리
        throw new SecurityException("JWT Token is invalid or expired");
    }
}
```
- `setSigningKey` : JWT 서명을 확인하기 위해 사용합니다. 서버의 비밀 키(secretkey)와 일치해야 합니다.
- `parseClaimsJws` : JWT 토큰을 파싱하여 Claims(payload 부분)를 추출합니다.

```java
 try {
            // JWT 토큰 검증 및 Claims 반환
            Claims claims = validateJwtToken(token);

            // 후처리: 클레임에서 필요한 정보 추출
            String username = claims.getSubject();  // 사용자 이름
            String roles = claims.get("roles", String.class);  // 사용자 역할 (예시)

            // 클레임에 포함된 정보로 보호된 리소스 제공
            return ResponseEntity.ok("Hello, " + username + "! You have the following roles: " + roles);

        } catch (SecurityException e) {
            return ResponseEntity.status(403).body("Invalid or expired token");
        }
```

```json
{
  "sub": "john_doe",       // 사용자의 고유 ID나 이름
  "roles": ["USER", "ADMIN"],  // 사용자의 역할
  "iat": 1613657864,       // 발급 시간
  "exp": 1613661464        // 만료 시간
}
```
> 주로 `sub`에 사용자의 고유 ID나 이름을 저장하기 때문에 `username` 에 Claims객체의 getSubject() 메서드를 통해 가져온다.

위의 방법을 통하여 매 요청마다 인증 정보를 검증할 수 있다.