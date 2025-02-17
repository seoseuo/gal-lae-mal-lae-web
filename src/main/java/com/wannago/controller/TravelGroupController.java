package com.wannago.controller;

import com.wannago.service.TravelGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.wannago.dto.TravelGroupDTO;
import com.wannago.dto.UserDTO;
import com.wannago.util.security.SecurityUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Map;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.wannago.dto.LocationDoDTO;
import com.wannago.dto.LocationSiDTO;
import com.wannago.dto.TravelDTO;
import com.wannago.dto.TourSpotsDTO;
import com.wannago.dto.ScheduleDTO;

@Log4j2
@RestController
@RequestMapping("/travelgroups")
public class TravelGroupController {

    @Autowired
    private TravelGroupService travelGroupService;

    @Autowired
    private SecurityUtil securityUtil;    

    // 모임 생성
    @PostMapping
    public ResponseEntity<String> createTravelGroup(@ModelAttribute TravelGroupDTO travelGroupDTO,
            @RequestParam("setGrProfile") MultipartFile file) {
        log.info("POST : /travelgroups");
        log.info(travelGroupDTO);
        log.info("파일명: {}", file.getOriginalFilename());
        log.info("파일 크기: {} bytes", file.getSize());
        log.info("파일 타입: {}", file.getContentType());

        UserDTO userDTO = securityUtil.getUserFromAuthentication();
        travelGroupService.createTravelGroup(travelGroupDTO, userDTO, file);
        return ResponseEntity.ok("모임 생성이 완료되었습니다.");
    }

    // 내 모임 목록 조회
    @GetMapping
    public ResponseEntity<List<TravelGroupDTO>> getTravelGroupList() {
        log.info("GET : /travelgroups");
        UserDTO userDTO = securityUtil.getUserFromAuthentication();
        return ResponseEntity.ok(travelGroupService.getTravelGroupList(userDTO.getUsIdx()));
    }

    // 특정 모임 조회
    @GetMapping("/{grIdx}")
    public ResponseEntity<Map<String, Object>> getTravelGroup(@PathVariable("grIdx") int grIdx) {
        log.info("GET : /travelgroups/{}", grIdx);
        return ResponseEntity.ok(travelGroupService.getTravelGroup(grIdx));
    }

    // 모임 권한 위임
    @PatchMapping("/{grIdx}/admin/{usIdx}")
    public ResponseEntity<String> updateAdmin(@PathVariable("grIdx") int grIdx, @PathVariable("usIdx") int usIdx) {
        log.info("PATCH : /travelgroups/{}/admin/{}", grIdx, usIdx);

        UserDTO userDTO = securityUtil.getUserFromAuthentication();

        log.info("grIdx : {}", grIdx);
        log.info("Old Admin : {}", userDTO.getUsIdx());
        log.info("New Admin : {}", usIdx);

        return ResponseEntity.ok(travelGroupService.updateAdmin(userDTO.getUsIdx(), usIdx, grIdx));
    }

    // 모임 탈퇴
    @PatchMapping("/{grIdx}/leave")
    public ResponseEntity<String> leaveTravelGroup(@PathVariable("grIdx") int grIdx) {
        log.info("PATCH : /travelgroups/{}/leave", grIdx);

        UserDTO userDTO = securityUtil.getUserFromAuthentication();
        return ResponseEntity.ok(travelGroupService.leaveTravelGroup(userDTO.getUsIdx(), grIdx));
    }

    // 모임 삭제
    @DeleteMapping("/{grIdx}")
    public ResponseEntity<String> deleteTravelGroup(@PathVariable("grIdx") int grIdx) {
        log.info("DELETE : /travelgroups/{}", grIdx);
        return ResponseEntity.ok(travelGroupService.deleteTravelGroup(grIdx));
    }

    // 모임 초대
    @PatchMapping("/{grIdx}/invite/{usIdx}")
    public ResponseEntity<String> inviteTravelGroup(@PathVariable("grIdx") int grIdx,
            @PathVariable("usIdx") int usIdx) {
        log.info("PATCH : /travelgroups/{}/invite/{}", grIdx, usIdx);
        return ResponseEntity.ok(travelGroupService.inviteTravelGroup(grIdx, usIdx));
    }


    
    // 여행지 생성 파트
    // 여행지 도 목록 가져오기
    @GetMapping("{grIdx}/travel/location/do")
    public ResponseEntity<List<LocationDoDTO>> getLocationDoList() {
        log.info("GET : /travelgroups/travel/location/do");
        return ResponseEntity.ok(travelGroupService.getLocationDoList());
    }

    // 여행지 도 예하 시 목록 가져오기
    @GetMapping("{grIdx}/travel/location/do/{ldIdx}")
    public ResponseEntity<List<LocationSiDTO>> getLocationSiList(@PathVariable("ldIdx") int ldIdx) {
        log.info("GET : /travelgroups/travel/location/do/{}", ldIdx);
        return ResponseEntity.ok(travelGroupService.getLocationSiList(ldIdx));
    }

    // 랜덤 여행지 추천
    @GetMapping("{grIdx}/travel/location/random")
    public ResponseEntity<LocationSiDTO> getRandomLocationSi() {
        log.info("GET : /travelgroups/travel/location/random");
        return ResponseEntity.ok(travelGroupService.getRandomLocationSi());
    }

    // 여행지 도 선정
    @PostMapping("{grIdx}/travel/location/do/{ldIdx}")
    public ResponseEntity<TravelDTO> selectLocationDo(@PathVariable("ldIdx") int ldIdx) {
        log.info("GET : /travelgroups/travel/location/do/{}", ldIdx);
        return ResponseEntity.ok(travelGroupService.selectLocationDo(ldIdx));
    }

    // 여행지 시 선정
    @PostMapping("{grIdx}/travel/location/do/si/{lsIdx}")
    public ResponseEntity<TravelDTO> selectLocationSi(@PathVariable("lsIdx") int lsIdx) {
        log.info("GET : /travelgroups/travel/location/si/{}", lsIdx);
        return ResponseEntity.ok(travelGroupService.selectLocationSi(lsIdx));
    }

    // 여행 기간 선정
    @PostMapping("{grIdx}/travel/period")
    public ResponseEntity<TravelDTO> selectTravelPeriod(@RequestBody TravelDTO newTravelDTO) {
        log.info("POST : /travelgroups/travel/schedule/period/{}", newTravelDTO.getTrStartTime());
        log.info("POST : /travelgroups/travel/schedule/period/{}", newTravelDTO.getTrEndTime());
        return ResponseEntity.ok(travelGroupService.selectTravelPeriod(newTravelDTO));
    }

    // 여행지 조회
    @GetMapping("{grIdx}/travel/{trIdx}")
    public ResponseEntity<Map<String, Object>> getTravel(@PathVariable("trIdx") int trIdx) {
        log.info("GET : /travelgroups/travel/{}", trIdx);
        return ResponseEntity.ok(travelGroupService.getTravel(trIdx));
    }

    // 시 예하 관광지 목록 조회
    @GetMapping("{grIdx}/travel/{trIdx}/{ldIdx}/{lsIdx}/tour-spots")
    public ResponseEntity<List<TourSpotsDTO>> getTourSpotList(@PathVariable("ldIdx") int ldIdx,
            @PathVariable("lsIdx") int lsIdx) {
        log.info("GET : /travelgroups/travel/{}/{}/tour-spots", ldIdx, lsIdx);
        return ResponseEntity.ok(travelGroupService.getTourSpotList(ldIdx, lsIdx));
    }

    // n일차 일정 장소 결정
    @PostMapping("{grIdx}/travel/{trIdx}/schedule")
    public ResponseEntity<String> selectSchedule(@RequestBody List<ScheduleDTO> scheduleDTOList) {
        log.info("POST : /travelgroups/travel/schedule");
        scheduleDTOList.forEach(schedule -> log.info(schedule.toString()));
        return ResponseEntity.ok(travelGroupService.selectSchedule(scheduleDTOList));
        
    }

}
