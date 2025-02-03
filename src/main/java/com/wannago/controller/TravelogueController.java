package com.wannago.controller;

import com.wannago.service.TravelogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/travelogue")
public class TravelogueController {

    private final TravelogueService travelogueService;

    @Autowired
    public TravelogueController(TravelogueService travelogueService) {
        this.travelogueService = travelogueService;
    }

    // 메서드들 (GET, POST, PUT, DELETE 등)
}
