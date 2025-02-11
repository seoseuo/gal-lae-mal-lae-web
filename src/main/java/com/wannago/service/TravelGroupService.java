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
        return travelGroupMapper.toDTOList(travelGroupRepository.findAll(usIdx));
    }

    // 특정 모임 조회
    // 특정 모임 조회 시 사용
    public Map<String, Object> getTravelGroup(int grIdx) {

        // 1. 요청 회원 권한 조회
        UserDTO userDTO = securityUtil.getUserFromAuthentication();
        Member member = memberRepository.findByGrIdxAndUsIdx(grIdx, userDTO.getUsIdx());
        if (member == null) {
            throw new RuntimeException("모임 참여 권한이 없습니다.");
        }

        Map<String, Object> travelGroupInfo = new HashMap<>();
        // 2. 모임 정보
        travelGroupInfo.put("travelGroup", travelGroupMapper.toDTO(travelGroupRepository.findById(grIdx)
                .orElseThrow(() -> new RuntimeException("모임을 찾을 수 없습니다."))));

        // 3. 모임 회원 목록
        travelGroupInfo.put("memberList", memberMapper.toDTOList(memberRepository.findByGrIdx(grIdx)));

        // 4. 모임 여행지 목록
        // travelGroupInfo.put("travelList",
        // travelMapper.toDTOList(travelRepository.findByGrIdx(grIdx)));

        return travelGroupInfo;
    }
}
