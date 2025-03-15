package com.wannago.controller;

import com.wannago.service.TravelogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import com.wannago.dto.TravelogueDTO;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;

@Log4j2
@RestController
@RequestMapping("/travelogues")
public class TravelogueController {

    @Autowired
    TravelogueService travelogueService;

    // 여행록 목록 ?page&size
    @GetMapping
    public ResponseEntity<Map<String, Object>> getTravelogueList(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size,
            @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        log.info("GET : /travelogues?page={}&size={}&keyword={}", page, size, keyword);
        return ResponseEntity.ok(travelogueService.getTravelogueList(page, size, keyword));
    }

    // 여행록 좋아요
    @PatchMapping("/like/{tlIdx}")
    public ResponseEntity<String> likeTravelogue(@PathVariable("tlIdx") int tlIdx) {
        log.info("PATCH : /travelogues/like/{}", tlIdx);
        return ResponseEntity.ok(travelogueService.likeTravelogue(tlIdx));
    }

}
