package com.wannago.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.wannago.repository.TravelGroupRepository;
import com.wannago.dto.TravelGroupDTO;
import com.wannago.entity.TravelGroup;
import com.wannago.mapper.TravelGroupMapper;
import com.wannago.dto.UserDTO;
import com.wannago.entity.Member;
import com.wannago.repository.MemberRepository;
import java.util.Date;
import java.util.List;

@Service
public class TravelGroupService {

    @Autowired
    private TravelGroupRepository travelGroupRepository;

    @Autowired
    private TravelGroupMapper travelGroupMapper;

    @Autowired
    private MemberRepository memberRepository;

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

    // 모임 목록 조회
    // 모임 목록 조회 시 사용
    public List<TravelGroupDTO> getTravelGroupList(UserDTO userDTO) {
        return travelGroupMapper.toDTOList(travelGroupRepository.findAll());
    }

    // 특정 모임 조회
    // 특정 모임 조회 시 사용
    public TravelGroupDTO getTravelGroup(int grIdx) {
        return travelGroupMapper.toDTO(travelGroupRepository.findById(grIdx).orElseThrow(() -> new RuntimeException("모임을 찾을 수 없습니다.")));
    }
}
