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
    public ResponseEntity<String> createTravelGroup(@RequestBody TravelGroupDTO travelGroupDTO) {
        log.info("POST : /travelgroups");
        log.info(travelGroupDTO);
        UserDTO userDTO = securityUtil.getUserFromAuthentication();
        travelGroupService.createTravelGroup(travelGroupDTO, userDTO);
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
}
