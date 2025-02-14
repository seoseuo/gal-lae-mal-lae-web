package com.wannago.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wannago.service.OpenApiService;
import com.wannago.dto.ResponseDTO;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private OpenApiService openApiService;
    
    @GetMapping("/saveCategory")
    public ResponseDTO saveCat1() {
        return openApiService.saveCateGory();
    }

    @GetMapping("/saveAreaCode")
    public ResponseDTO saveAreaCode() {
        return openApiService.saveAreaCode();
    }

    @GetMapping("/saveTourSpot")
    public ResponseDTO saveTourSpot() {
        return openApiService.saveTourSpot();
    }
}
