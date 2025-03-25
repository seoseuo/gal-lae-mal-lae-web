package com.wannago.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.wannago.dto.TravelogueDTO;
import com.wannago.dto.TravelogueLikeDTO;
import com.wannago.entity.Travelogue;
import com.wannago.entity.TravelogueLike;
import com.wannago.repository.TravelogueRepository;
import com.wannago.repository.TravelogueLikeRepository;
import java.util.List;
import com.wannago.mapper.TravelogueMapper;
import com.wannago.mapper.TravelogueLikeMapper;
import java.util.ArrayList;
import java.util.stream.Collectors;
import com.wannago.util.security.SecurityUtil;
import lombok.extern.log4j.Log4j2;
import com.wannago.dto.UserDTO;
import com.wannago.entity.User;
import com.wannago.repository.UserRepository;

@Log4j2
@Service
public class TravelogueService {

    @Autowired
    private TravelogueRepository travelogueRepository;

    @Autowired
    private TravelogueLikeRepository travelogueLikeRepository;

    @Autowired
    private TravelogueMapper travelogueMapper;

    @Autowired
    private TravelogueLikeMapper travelogueLikeMapper;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private UserRepository userRepository;

    // 여행록 목록
    public Map<String, Object> getTravelogueList(int page, int size, String keyword) {
        if(keyword == null || keyword.isEmpty()) {
            keyword = "";
        }
        Map<String, Object> travelogueList = new HashMap<>();

        // 1. 여행록 목록 페이지네이션 20개씩
        Pageable pageable = PageRequest.of(page, size);

        // 2. 여행록 목록 가져오기 tlState = 1 && tlPublic = 1
        Page<Travelogue> traveloguePage = travelogueRepository.findByTlStateAndTlPublic(1, 1, pageable, keyword);        

        // 2-1. Mapper로 DTO로 변환
        List<TravelogueDTO> travelogueDTOList = travelogueMapper.toDTOList(traveloguePage.getContent());

        // 2-2. travelogueDTOList 의 usIdx 를 가지고 있는 배열 생성
        for(TravelogueDTO travelogueDTO : travelogueDTOList) {
            User user = userRepository.findByUsIdx(travelogueDTO.getUsIdx()).orElseThrow(() -> new RuntimeException("여행록 유저 정보를 찾을 수 없습니다."));
            travelogueDTO.setUsName(user.getUsName());
            travelogueDTO.setUsProfile(user.getUsProfile());
        }
        

        // 3.여행록 좋아요 리스트 가져오기
        // 3-1 travelogueList 의 tlIdx 를 가지고 있는 배열 생성
        List<Integer> tlIdxList = travelogueDTOList.stream()
                .map(travelogue -> travelogue.getTlIdx())
                .collect(Collectors.toList());

        // 3-2 tlIdxList 를 가지고 있는 travelogueLike 목록 가져오기
        // 3-3 travelogueLikeList 를 travelogueLikeDTOList 로 변환
        // 3-4 travelogueLikeDTOList 를 리턴 객체에 저장
        List<TravelogueLikeDTO> travelogueLikeDTOList = travelogueLikeMapper.toDTOList(travelogueLikeRepository.findByTlIdxIn(tlIdxList));

        travelogueList.put("travelogueList", travelogueDTOList);
        travelogueList.put("travelogueLikeList", travelogueLikeDTOList);

        return travelogueList;
    }

    // 여행록 좋아요
    public String likeTravelogue(int tlIdx) {
        // 유저 정보 가져오기        
        UserDTO userDTO = securityUtil.getUserFromAuthentication();
        int usIdx = userDTO.getUsIdx();

        // 좋아요 체크
        // 좋아요를 안했으면 좋아요를, 했으면 좋아요 취소를.        
        if (travelogueLikeRepository.findByTlIdxAndUsIdx(tlIdx, usIdx) == null) {
            travelogueLikeRepository.save(TravelogueLike.builder()
                    .tlIdx(tlIdx)
                    .usIdx(usIdx)
                    .build());
            return "좋아요 성공";
        }
        else {
            travelogueLikeRepository.deleteByTlIdxAndUsIdx(tlIdx, usIdx);
            return "좋아요 취소";
        }

    }

}
