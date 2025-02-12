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
import java.util.stream.Collectors;
import com.wannago.util.security.SecurityUtil;
import com.wannago.mapper.MemberMapper;
import java.util.HashMap;
import lombok.extern.log4j.Log4j2;
import java.util.Comparator;

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
    private SecurityUtil securityUtil;

    // 모임 생성
    // 모임 생성 시 사용
    public void createTravelGroup(TravelGroupDTO travelGroupDTO, UserDTO userDTO) {
        // 생성 날짜 지정
        travelGroupDTO.setGrCreatedAt(new Date());

        // 1. TravelGroup 생성
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

        // 1. 요청 회원 권한 조회
        UserDTO userDTO = securityUtil.getUserFromAuthentication();
        Member member = memberRepository.findByGrIdxAndUsIdx(grIdx, userDTO.getUsIdx());
        Map<String, Object> travelGroupInfo = new HashMap<>();

        if (member == null) {
            travelGroupInfo.put("error", "모임 참여 권한이 없습니다.");
            return travelGroupInfo;
        }

        // 2. 모임 정보
        travelGroupInfo.put("travelGroup", travelGroupMapper.toDTO(travelGroupRepository.findById(grIdx)
                .orElseThrow(() -> new RuntimeException("모임을 찾을 수 없습니다."))));

        // 3. 모임 회원 목록
        // 3-1. 회장을 맨 앞으로 옮기기
        List<MemberDTO> memberList = memberMapper.toDTOList(memberRepository.findByGrIdx(grIdx));
        memberList.sort(Comparator.comparing(MemberDTO::getMeRole));
        travelGroupInfo.put("memberList", memberList);

        // 4. 모임 여행지 목록
        // travelGroupInfo.put("travelList",
        // travelMapper.toDTOList(travelRepository.findByGrIdx(grIdx)));

        return travelGroupInfo;
    }

    // 모임 회장 권한 변경
    public void updateAdmin(int oldUsIdx, int newUsIdx, int grIdx) {
        
        // 1. 기존 회장 권한을 USER로 변경
        memberRepository.updateMeRoleByGrIdxAndUsIdx(grIdx, oldUsIdx, Member.MemberRole.MEMBER);
        
        // 2. 새로운 회장 권한을 ADMIN으로 변경
        memberRepository.updateMeRoleByGrIdxAndUsIdx(grIdx, newUsIdx, Member.MemberRole.ADMIN);
    }
}
