![image](https://github.com/user-attachments/assets/53b05e13-180f-4208-8e65-9e3c3850957d)<img src="https://github.com/user-attachments/assets/04d58dd4-1556-4bfa-b158-355bed77e5c8" style="width: 250px; height: 250px;" />

### 갈래말래
> 갈래말래, 여행 모임 일정 관리 및 장소 추천 커뮤니티 웹 서비스

<a href="https://chivalrous-saffron-326.notion.site/1d10ba93975b8086a976d70dd9224326?pvs=4"><img src="https://img.shields.io/badge/Notion 링크 보기 -E6E6E6?style=for-the-badge&logo=notion&logoColor=black" /></a>
<a href="https://chivalrous-saffron-326.notion.site/1f70ba93975b80489b3dc3e98f0bf2b2?pvs=4"><img src="https://img.shields.io/badge/갈래말래 산출물 -E6E6E6?style=for-the-badge&logo=notion&logoColor=black" /></a>
<br><a href="https://velog.io/@seuo/series/%EA%B0%88%EB%9E%98%EB%A7%90%EB%9E%98" target="_blank" rel="noopener noreferrer">
  <img src="https://img.shields.io/badge/[시리즈 | 갈래말래 : velog]-20C997?style=for-the-badge&logo=velog&logoColor=black&labelColor=20C997&color=20C997" />
</a>
<hr>

# 개요

![image](https://github.com/user-attachments/assets/b1812efd-3f5a-43c3-8a66-ac1efeda39ef)
![image](https://github.com/user-attachments/assets/52804a0d-efb5-4200-9e24-e7878317e9fd)
![image](https://github.com/user-attachments/assets/80849fc6-c285-4fd7-91a2-9c79756dbde8)
![image](https://github.com/user-attachments/assets/b158e18f-322c-43a4-946e-db55ca51d1cc)
![image](https://github.com/user-attachments/assets/7c836482-9cb9-4b5d-b5c2-bfdbc388b22e)
![image](https://github.com/user-attachments/assets/db832044-9498-4079-bbcb-6d7b69708ef3)







# 주요 기능

## JWT 토큰

![image](https://github.com/user-attachments/assets/b34fff96-ec97-40f1-8428-c0191e069c1f)


> JWT 토큰 기반 인증 방식 도입으로 사용자 인증 및 보안 강화
> 

## 모임 여행 생성 및 관리

![image](https://github.com/user-attachments/assets/3c26a34b-c5d8-45ac-b581-0fe5a17a71ea)

> 모임 여행 생성 시, 각 도/시, 날짜 단계별 데이터를 Redis에 순차적으로 저장 처리
```java
// 여행지 도 선정
    public TravelDTO selectLocationDo(int ldIdx, int grIdx) {

        // 2. travleDTO 객체 생성
        TravelDTO travelDTO = new TravelDTO();
        // 3. travleDTO 객체에 grIdx, ldIdx, state = 1, createdAt 현재 시각 setter로 등록

        travelDTO.setGrIdx(grIdx);
        travelDTO.setLdIdx(ldIdx);
        travelDTO.setTrState(1);
        travelDTO.setTrCreatedAt(new Date());

        log.info("travelDTO  {}", travelDTO);

        // 4. redis에 nowTravelDTO 키로 저장
        redisService.setTravelInfo(grIdx + "nowTravelDTO", travelDTO);

        return travelDTO;
    }

    // 여행지 시 선정
    public TravelDTO selectLocationSi(int lsIdx, int grIdx) {
        // 1. redis에서 nowTravelDTO 가져오기
        // 키 : 값 문자열 형태인 Object 타입을 TravelDTO 타입으로 변환
        TravelDTO travelDTO = (TravelDTO) redisService.getTravelInfo(grIdx + "nowTravelDTO");

        // 2. travelDTO 에 lsIdx 선정
        travelDTO.setLsIdx(lsIdx);
        log.info("travelDTO  {}", travelDTO);
        // 3. redis에 nowTravelDTO 키로 저장
        redisService.setTravelInfo(grIdx + "nowTravelDTO", travelDTO);
        return travelDTO;
    }

    // 여행 기간 선정
    public TravelDTO selectTravelPeriod(TravelDTO newTravelDTO, int grIdx) {
        // 1. redis에 nowTravelDTO 가져오기
        TravelDTO travelDTO = (TravelDTO) redisService.getTravelInfo(grIdx + "nowTravelDTO");

        // 2. travelDTO 에 trStartTime, trEndTime 선정
        travelDTO.setGrIdx(newTravelDTO.getGrIdx());
        travelDTO.setTrStartTime(newTravelDTO.getTrStartTime());
        travelDTO.setTrEndTime(newTravelDTO.getTrEndTime());

        // 3. trPeriod 선정
        // ISO 8601 형식에서 날짜의 차이를 구해야 함. ex) 2025-12-18T00:00:00.000+0900 를 20251218 형식으로
        // 날짜 포맷 설정
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        // travelDTO의 시작과 끝 시간을 "yyyy-MM-dd" 형식으로 변환
        // 날짜 차이를 구하기
        // 0-9 까지 인덱싱 한 다음 '-' 제거 후 정수형으로 변환
        int period = Integer.parseInt(outputFormat.format(travelDTO.getTrEndTime()).substring(0, 10).replace("-", ""))
                - Integer.parseInt(outputFormat.format(travelDTO.getTrStartTime()).substring(0, 10).replace("-", ""))
                + 1;

        travelDTO.setTrPeriod(period);

        // 3. travel 테이블에 저장
        int trIdx = travelRepository.save(travelMapper.toEntity(travelDTO)).getTrIdx();
        travelDTO.setTrIdx(trIdx);

        // 4. redis에 nowTravelDTO 삭제
        redisService.deleteTravelInfo(grIdx + "nowTravelDTO");

        return travelDTO;
    }
```

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
>
