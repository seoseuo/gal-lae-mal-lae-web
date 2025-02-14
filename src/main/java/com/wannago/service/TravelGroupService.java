package com.wannago.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.wannago.repository.TravelGroupRepository;
import com.wannago.dto.TravelGroupDTO;
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

    @Value("${file.image.upload-path}")
    private String fileUploadPath;

    // 모임 생성
    // 모임 생성 시 사용
    public void createTravelGroup(TravelGroupDTO travelGroupDTO, UserDTO userDTO, MultipartFile file) {
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
    }

    // 내 모임 목록 조회
    // 내 모임 목록 조회 시 사용
    public List<TravelGroupDTO> getTravelGroupList(int usIdx) {
        // 1. Member 테이블에서 내가 속한 grIdx 가져오기
        List<Integer> grIdxList = memberRepository.findGrIdxByUsIdx(usIdx);
        log.info("grIdxList  {}", grIdxList);

        // 2. TravelGroup 테이블에서 grIdx 리스트들로 조회해서 목록 가져오기
        return travelGroupMapper.toDTOList(travelGroupRepository.findAllByGrIdxIn(grIdxList));

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
        // travelGroupInfo.put("travelList",
        // travelMapper.toDTOList(travelRepository.findByGrIdx(grIdx)));

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
        memberRepository.deleteByGrIdxAndUsIdx(grIdx, usIdx);
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
}