package com.wannago.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.wannago.repository.TravelGroupRepository;
import com.wannago.dto.TravelGroupDTO;
import com.wannago.dto.TravelViewDTO;
import com.wannago.entity.TravelGroup;
import com.wannago.mapper.TravelGroupMapper;
import com.wannago.dto.UserDTO;
import com.wannago.dto.MemberDTO;
import com.wannago.entity.Member;
import com.wannago.repository.MemberRepository;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.wannago.mapper.MemberMapper;
import java.util.HashMap;
import lombok.extern.log4j.Log4j2;
import java.util.Comparator;
import com.wannago.mapper.UserMapper;
import com.wannago.repository.UserRepository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import com.wannago.dto.LocationSiDTO;
import com.wannago.mapper.LocationSiMapper;
import com.wannago.repository.LocationDoRepository;
import com.wannago.dto.LocationDoDTO;
import com.wannago.mapper.LocationDoMapper;
import com.wannago.repository.LocationSiRepository;
import com.wannago.entity.LocationSi;
import java.util.Random;
import com.wannago.repository.TravelRepository;
import com.wannago.mapper.TravelMapper;
import com.wannago.dto.TravelDTO;
import com.wannago.dto.ScheduleDTO;
import com.wannago.repository.ScheduleRepository;
import com.wannago.mapper.ScheduleMapper;
import java.text.SimpleDateFormat;
import com.wannago.entity.Travelogue;
import com.wannago.repository.TravelogueRepository;
import com.wannago.mapper.TravelogueMapper;
import com.wannago.dto.TourSpotsDTO;
import com.wannago.repository.TourSpotsRepository;
import com.wannago.mapper.TourSpotsMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.wannago.entity.TourSpots;
import com.wannago.dto.TravelogueDTO;
import com.wannago.util.security.SecurityUtil;
import java.util.stream.Collectors;
import com.wannago.mapper.TravelogueLikeMapper;
import com.wannago.repository.TravelogueLikeRepository;
import com.wannago.dto.TravelogueLikeDTO;
import java.util.ArrayList;
import java.util.Optional;

@Log4j2
@Service
public class TravelGroupService {

    @Autowired
    private TravelGroupRepository travelGroupRepository;

    @Autowired
    private TravelGroupMapper travelGroupMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationSiMapper locationSiMapper;

    @Autowired
    private LocationDoMapper locationDoMapper;

    @Autowired
    private LocationDoRepository locationDoRepository;

    @Autowired
    private LocationSiRepository locationSiRepository;

    @Autowired
    private TravelMapper travelMapper;

    @Autowired
    private TravelRepository travelRepository;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private TravelogueRepository travelogueRepository;

    @Autowired
    private TravelogueMapper travelogueMapper;

    @Autowired
    private TourSpotsRepository tourSpotsRepository;

    @Autowired
    private TourSpotsMapper tourSpotsMapper;

    @Value("${file.image.upload-path}")
    private String fileUploadPath;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private TravelogueLikeMapper travelogueLikeMapper;

    @Autowired
    private TravelogueLikeRepository travelogueLikeRepository;

    // 모임 생성
    // 모임 생성 시 사용
    public String createTravelGroup(TravelGroupDTO travelGroupDTO, UserDTO userDTO, MultipartFile file) {
        // 생성 날짜 지정
        travelGroupDTO.setGrCreatedAt(new Date());

        // 0. 이미지 파일 이름 설정
        // 이름은 이 그룹이름_grProfile.확장자 형식으로 저장
        String grProfile = travelGroupDTO.getGrName() + "_grProfile." + file.getOriginalFilename().split("\\.")[1];
        travelGroupDTO.setGrProfile(grProfile);

        // 0-1. 이미지 파일 해당 경로에 저장
        File newFile = new File(fileUploadPath + grProfile);
        try {
            Files.copy(file.getInputStream(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }

        // 0-2. 이미지 파일 세팅
        travelGroupDTO.setGrProfile(grProfile);

        // 1. TravelGroup 생성
        travelGroupDTO.setGrState(1);
        TravelGroup travelGroup = travelGroupMapper.toEntity(travelGroupDTO);
        travelGroup = travelGroupRepository.save(travelGroup);

        // 2. 생성하는 회원 Member 회장 등록
        Member member = Member.builder()
                .grIdx(travelGroup.getGrIdx())
                .usIdx(userDTO.getUsIdx())
                .meRole(Member.MemberRole.ADMIN)
                .build();
        memberRepository.save(member);

        return "모임 생성이 완료되었습니다.";
    }

    // 내 모임 목록 조회
    // 내 모임 목록 조회 시 사용
    public List<TravelGroupDTO> getTravelGroupList(int usIdx) {
        // 1. Member 테이블에서 내가 속한 grIdx 가져오기
        List<Integer> grIdxList = memberRepository.findGrIdxByUsIdx(usIdx);
        log.info("grIdxList  {}", grIdxList);

        // 2. TravelGroup 테이블에서 grIdx 리스트들로 조회해서 목록 가져오기
        // 3. 각 객체의 DTO 필드를 꺼내서 쿼리랑 연동 후 set해주기
        List<TravelGroupDTO> travelGroupList = travelGroupMapper
                .toDTOList(travelGroupRepository.findAllByGrIdxIn(grIdxList));

        for (TravelGroupDTO travelGroup : travelGroupList) {
            travelGroup.setGrCount(memberRepository.countByGrIdx(travelGroup.getGrIdx()));
            travelGroup.setGrLdList(locationDoRepository
                    .findLocationNamesByLdIdxList(travelRepository.findLdIdxByGrIdx(travelGroup.getGrIdx())));

        }

        return travelGroupList;

    }

    // 특정 모임 조회
    // 특정 모임 조회 시 사용
    public Map<String, Object> getTravelGroup(int grIdx) {

        // 1. 리턴 객체 생성
        Map<String, Object> travelGroupInfo = new HashMap<>();

        // 2. 모임 정보
        travelGroupInfo.put("travelGroup", travelGroupMapper.toDTO(travelGroupRepository.findById(grIdx)
                .orElseThrow(() -> new RuntimeException("모임을 찾을 수 없습니다."))));

        // 3. 모임 회원 목록
        // 3-1. 회장을 맨 앞으로 옮기기
        List<MemberDTO> memberList = memberMapper.toDTOList(memberRepository.findByGrIdx(grIdx));
        memberList.sort(Comparator.comparing(MemberDTO::getMeRole));
        memberList.forEach(member -> {
            member.setMeUser(userMapper.toDTO(userRepository.findById(member.getUsIdx())
                    .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."))));
        });
        travelGroupInfo.put("memberList", memberList);

        // 4. 모임 여행지 목록
        List<TravelDTO> travelList = travelMapper.toDTOList(travelRepository.findByGrIdx(grIdx));

        List<TravelViewDTO> travelViewList = new ArrayList<>();
        TravelViewDTO travelViewDTO;
        for (int i = 0; i < travelList.size(); i++) {
            travelViewDTO = new TravelViewDTO();
            travelViewDTO.setTrIdx(travelList.get(i).getTrIdx());

            // 1부터 8사이라면
            if (travelList.get(i).getLdIdx() > 0 && travelList.get(i).getLdIdx() < 9) {
                travelViewDTO.setLdName(locationDoRepository.findLocationNameByLdIdx(travelList.get(i).getLdIdx())
                        .orElseThrow(() -> new RuntimeException("여행지를 찾을 수 없습니다.")));
            } else {
                travelViewDTO.setLdName(locationDoRepository.findLocationNameByLdIdx(travelList.get(i).getLdIdx())
                        .orElseThrow(() -> new RuntimeException("여행지를 찾을 수 없습니다.")));
                travelViewDTO.setLsName(locationSiRepository
                        .findLocationNameByLsIdx(travelList.get(i).getLdIdx(), travelList.get(i).getLsIdx())
                        .orElseThrow(() -> new RuntimeException("여행지를 찾을 수 없습니다.")));
            }
            travelViewDTO.setTlImgList(travelogueRepository.findTlImageByTrIdx(travelList.get(i).getTrIdx()));
            travelViewList.add(travelViewDTO);
        }

        travelGroupInfo.put("travelList", travelViewList);

        return travelGroupInfo;
    }

    // 모임 회장 권한 변경
    public String updateAdmin(int oldUsIdx, int newUsIdx, int grIdx) {

        // 1. 기존 회장 권한을 USER로 변경
        memberRepository.updateMeRoleByGrIdxAndUsIdx(grIdx, oldUsIdx, Member.MemberRole.MEMBER);

        // 2. 새로운 회장 권한을 ADMIN으로 변경
        memberRepository.updateMeRoleByGrIdxAndUsIdx(grIdx, newUsIdx, Member.MemberRole.ADMIN);

        return "모임 회장이 변경되었습니다.";
    }

    // 모임 탈퇴
    public String leaveTravelGroup(int usIdx, int grIdx) {
        // 1. 해당 회원이 이 모임의 회장인지 확인
        // 회장이라면 모임 탈퇴 불가
        if (memberRepository.findByGrIdxAndUsIdx(grIdx, usIdx).getMeRole() == Member.MemberRole.ADMIN) {
            return "회장은 모임을 탈퇴할 수 없습니다.\n권한을 위임하세요.";
        }

        // 2. 회장이 아니라면 모임 탈퇴
        // 2-1 Member 테이블에서 해당 모임 회원 삭제
        memberRepository.deleteByGrIdxAndUsIdx(grIdx, usIdx);

        // 2-2 Travelogue 테이블에서 해당 모임 회원이 남긴 글의 tlState를 0으로 변경
        travelogueRepository.updateTlStateByGrIdxAndUsIdx(grIdx, usIdx);

        return "모임을 탈퇴했습니다.";
    }

    // 모임 삭제
    public String deleteTravelGroup(int grIdx) {

        // 1. MEMBER 테이블에서 해당 모임 회원 삭제
        memberRepository.deleteByGrIdx(grIdx);

        // 2. TRAVELGROUP 테이블에서 해당 모임 grStatus를 0 으로 변경
        // 2-1. 모임 삭제 시간 추가
        Date grDeletedAt = new Date();
        travelGroupRepository.updateGrStatusByGrIdx(grIdx, grDeletedAt);
        return "모임을 삭제했습니다.";
    }

    // 모임 초대
    public String inviteTravelGroup(int grIdx, int usIdx) {
        // 1. 모임 초대
        memberRepository.save(Member.builder()
                .grIdx(grIdx)
                .usIdx(usIdx)
                .meRole(Member.MemberRole.MEMBER)
                .build());
        return "모임 초대가 완료되었습니다.";
    }

    // 여행지 생성 파트
    // 여행지 도 목록 가져오기
    public List<LocationDoDTO> getLocationDoList() {
        return locationDoMapper.toDTOList(locationDoRepository.findAll());
    }

    // 여행지 시 목록 가져오기
    public List<LocationSiDTO> getLocationSiList(int ldIdx) {
        return locationSiMapper.toDTOList(locationSiRepository.findByLdIdx(ldIdx));
    }

    // 랜덤 여행지 추천
    public LocationSiDTO getRandomLocationSi() {
        // DB에서 랜덤으로 locationSi 데이터 조회
        List<LocationSi> locationSiList = locationSiRepository.findAll();
        // 랜덤으로 하나 뽑기
        LocationSi locationSi = locationSiList.get(new Random().nextInt(locationSiList.size()));

        LocationSiDTO locationSiDTO = locationSiMapper.toDTO(locationSi);
        // ldIdx 를 통해 ldName 가져오기
        locationSiDTO.setLdName(locationDoRepository.findById(locationSi.getLdIdx()).get().getLdName());

        return locationSiDTO;
    }

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

    // 여행 조회
    public Map<String, Object> getTravel(int trIdx) {

        // 1. 리턴 객체 생성
        Map<String, Object> travelInfo = new HashMap<>();

        // 2. 여행 정보 가져오기 optional타입으로 받아옴.;
        // 2-1. 여행 정보 리턴 객체에 저장
        travelInfo.put("travel", travelMapper
                .toDTO(travelRepository.findByTrIdx(trIdx).orElseThrow(() -> new RuntimeException("여행을 찾을 수 없습니다."))));

        // 2. 여행 일정 목록 가져오기
        // 2-1. 여행 일정 목록 리턴 객체에 저장
        List<ScheduleDTO> scheduleDTOList = scheduleMapper.toDTOList(scheduleRepository.findByTrIdx(trIdx));
        TourSpots tourSpots;

        for (ScheduleDTO scheduleDTO : scheduleDTOList) {
            log.info("scheduleDTO.getTsIdx() {}", scheduleDTO.getTsIdx());
            Optional<TourSpots> optionalTourSpots = tourSpotsRepository.findById(scheduleDTO.getTsIdx());
            // tsIdx 를 통해 이름, 첫번째 이미지, 주소, 전화번호 가져오기 -> Optional 타입으로 받아옴.
            tourSpots = optionalTourSpots.orElseThrow(() -> new RuntimeException("tour_spots 데이터를 찾을 수 없습니다."));
            log.info("tourSpots {}", tourSpots);

            scheduleDTO.setTsName(tourSpots.getTsName());
            scheduleDTO.setTsFirstImage(tourSpots.getTsFirstImage());
            scheduleDTO.setTsAddr1(tourSpots.getTsAddr1());
            scheduleDTO.setTsTel(tourSpots.getTsTel());

            log.info("scheduleDTO {}", scheduleDTO);
        }

        travelInfo.put("scheduleList", scheduleDTOList);

        // 3. 여행록 가져오기 tlState = 1 인 데이터만 가져오기
        // 3-1. 여행록 리턴 객체에 저장
        List<Travelogue> travelogueList = travelogueRepository.findByTrIdx(trIdx);
        travelInfo.put("travelogueList", travelogueMapper.toDTOList(travelogueList));

        // 4.여행록 좋아요 리스트 가져오기
        // 4-1 travelogueList 의 tlIdx 를 가지고 있는 배열 생성
        List<Integer> tlIdxList = travelogueList.stream()
                .map(travelogue -> travelogue.getTlIdx())
                .collect(Collectors.toList());

        // 4-2 tlIdxList 를 가지고 있는 travelogueLike 목록 가져오기
        // 4-3 travelogueLikeList 를 travelogueLikeDTOList 로 변환
        // 4-4 travelogueLikeDTOList 를 리턴 객체에 저장
        travelInfo.put("travelogueLikeList",
                travelogueLikeMapper.toDTOList(travelogueLikeRepository.findByTlIdxIn(tlIdxList)));

        return travelInfo;
    }

    // 여행지 삭제
    public String deleteTravel(int trIdx) {

        // 1. 여행지 trIdx를 가지고 있는 데이터들의 tlImage 목록을 가져와서 폴더에서 이미지 파일 지우기
        List<String> tlImageList = travelogueRepository.findByTrIdx(trIdx).stream()
                .map(travelogue -> travelogue.getTlImage())
                .collect(Collectors.toList());

        tlImageList.forEach(tlImage -> {
            File file = new File(fileUploadPath + tlImage);
            file.delete();
        });

        // 1-1. 여행지 trIdx를 가지고 있는 travelogue 의 tlState를 0으로 변경
        travelogueRepository.updateTlStateByTrIdx(trIdx);

        // 2. 여행지 trIdx를 가지고 있는 travel 의 trState를 0으로 변경
        travelRepository.updateTrStateByTrIdx(trIdx);
        return "여행지가 삭제되었습니다.";
    }

    // 시 예하 관광지 목록 조회
    public Page<TourSpotsDTO> getTourSpotList(TourSpotsDTO tourSpotsDTO, Pageable pageable) {
        Page<TourSpots> tourSpotsPage;

        // 필터링 포함
        if (tourSpotsDTO.getLsIdx() == null) {
            tourSpotsPage = tourSpotsRepository.findByLdIdxAndC1CodeAndTsName(
                    tourSpotsDTO.getLdIdx(), tourSpotsDTO.getC1Code(), tourSpotsDTO.getTsName(), pageable);
        } else {
            tourSpotsPage = tourSpotsRepository.findByLsIdxAndC1CodeAndTsName(
                    tourSpotsDTO.getLdIdx(), tourSpotsDTO.getLsIdx(), tourSpotsDTO.getC1Code(),
                    tourSpotsDTO.getTsName(), pageable);
        }

        return tourSpotsPage.map(tourSpotsMapper::toDTO);
    }

    // n일차 일정 장소 결정
    public String selectSchedule(List<ScheduleDTO> scheduleDTOList) {
        // 3. schedule 테이블에 저장
        scheduleRepository.saveAll(scheduleMapper.toEntityList(scheduleDTOList));
        return "일정이 선정되었습니다.";
    }

    // 일정 삭제
    public String deleteSchedule(int scIdx) {
        scheduleRepository.deleteById(scIdx);
        return "일정이 삭제되었습니다.";
    }

    // 일정 시간 수정
    public String updateSchedule(ScheduleDTO scheduleDTO) {
        scheduleRepository.updateSchedule(scheduleDTO.getScIdx(), scheduleDTO.getScStartTime(),
                scheduleDTO.getScEndTime());
        return "일정 시간이 수정되었습니다.";
    }

    // 여행록 작성
    public String writeTravelogue(TravelogueDTO travelogueDTO, MultipartFile file) {
        // 여행록 이미지 저장
        // 파일의 이름은 해당 trIdx + _ + 해당 작성자 이메일 + _ + travelogue + _ + 현재 시간 + . + 확장자

        // 유저 이메일 가져오기
        SecurityUtil securityUtil = new SecurityUtil();
        UserDTO userDTO = securityUtil.getUserFromAuthentication();
        String tlImage = travelogueDTO.getTrIdx() + "_" + userDTO.getUsEmail() + "_travelogue_"
                + new Date().getTime() + "." + file.getOriginalFilename().split("\\.")[1];

        // 여행록 이미지 저장
        File newFile = new File(fileUploadPath + tlImage);
        try {
            Files.copy(file.getInputStream(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }

        // usIdx 세팅
        travelogueDTO.setUsIdx(userDTO.getUsIdx());

        // tlImage 세팅
        travelogueDTO.setTlImage(tlImage);

        // tlState 세팅
        travelogueDTO.setTlState(1);

        // 여행록 DB에 저장
        travelogueRepository.save(travelogueMapper.toEntity(travelogueDTO));
        return "여행록이 작성되었습니다.";
    }

    // 여행록 수정
    public String updateTravelogue(int tlIdx, TravelogueDTO travelogueDTO, MultipartFile file) {

        // 기존 이미지 파일 제거를 위한 기존 값에서 tlImage 가져오기
        String tlImage = travelogueRepository.findById(tlIdx).get().getTlImage();

        // usIdx 가져오기
        UserDTO userDTO = securityUtil.getUserFromAuthentication();

        // 기존 이미지 파일 제거
        File oldFile = new File(fileUploadPath + tlImage);
        oldFile.delete();

        // 여행록 이미지 저장
        // 파일의 이름은 해당 trIdx + _ + 해당 작성자 이메일 + _ + travelogue + _ + 현재 시간 + . + 확장자

        // 유저 이메일 가져오기

        tlImage = travelogueDTO.getTrIdx() + "_" + userDTO.getUsEmail() + "_travelogue_"
                + new Date().getTime() + "." + file.getOriginalFilename().split("\\.")[1];

        // 여행록 이미지 저장
        File newFile = new File(fileUploadPath + tlImage);
        try {
            Files.copy(file.getInputStream(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }

        // usIdx 세팅
        travelogueDTO.setUsIdx(userDTO.getUsIdx());

        // tlImage 세팅
        travelogueDTO.setTlImage(tlImage);

        log.info("travelogueDTO  {}", travelogueDTO);

        // 변경된 부분만 업데이트
        travelogueRepository.updateTravelogue(tlIdx, travelogueDTO.getTlTitle(), travelogueDTO.getTlContent(),
                travelogueDTO.getTlImage(), travelogueDTO.getTlPublic());
        return "여행록이 수정되었습니다.";
    }

    // 여행록 삭제
    public String deleteTravelogue(int tlIdx) {

        // tlIdx에 해당하는 이미지 값 가져오기
        String tlImage = travelogueRepository.findById(tlIdx).get().getTlImage();

        // 이미지 파일 제거
        File file = new File(fileUploadPath + tlImage);
        file.delete();

        // tlIdx에 해당하는 여행록 삭제
        travelogueRepository.deleteTravelogue(tlIdx);

        return "여행록이 삭제되었습니다.";
    }

    // 초대를 위한 유저 이메일 검색
    public List<UserDTO> searchUser(String usEmail, int grIdx) {
        return userMapper.toDTOList(userRepository.findByUsEmailContaining(usEmail, grIdx));
    }

    // 랜덤 여행 미리보기 조회
    public List<TourSpotsDTO> getRandomTravelPreview(int grIdx, Integer ldIdx, Integer lsIdx) {

        // 여행지는 10개를 추천한다. 단, tsFirstImage 가 null 인 경우 제외 <- 쿼리에서 결정시킴

        // 1. 만약 lsIdx가 null이면 랜덤 여행지 추천
        if (lsIdx == null || lsIdx == 0) {
            return tourSpotsMapper.toDTOList(tourSpotsRepository.findByLdIdx(ldIdx));
        }

        // 2. 만약 lsIdx가 null이 아니면 해당 여행지 추천
        else {
            return tourSpotsMapper.toDTOList(tourSpotsRepository.findByLsIdx(lsIdx, ldIdx));
        }

    }
}
