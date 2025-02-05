package com.wannago.controller;

import com.wannago.service.TravelGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/travelgroup")
public class TravelGroupController {


    private final TravelGroupService travelGroupService;

    @Autowired
    public TravelGroupController(TravelGroupService travelGroupService) {
        this.travelGroupService = travelGroupService;
    }

    // 메서드들 (GET, POST, PUT, DELETE 등)
}
