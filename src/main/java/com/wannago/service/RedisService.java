package com.wannago.service;

import java.util.concurrent.TimeUnit;

import com.wannago.dto.TravelDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.LinkedHashMap;
import lombok.extern.log4j.Log4j2;
import java.util.Date;

@Log4j2
@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // key값 code를 value로 하여 3분동안 저장한다.
    public void set(String key, String value, int seconds) {
        ValueOperations<String, Object> valOperations = redisTemplate.opsForValue();
        // 만료기간 3분
        valOperations.set(key, value, seconds, TimeUnit.SECONDS);
    }

    // key값인 email에 있는 value를 가져온다.
    public String get(String key) {
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("키 값이 비어있습니다");
        }
        ValueOperations<String, Object> valOperations = redisTemplate.opsForValue();
        Object code = valOperations.get(key);
        if (code == null) {
            return null;
        }
        return code.toString();
    }

    // key값인 email에 있는 value를 삭제한다.
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    // TravelGroup 추가 부분

    // 현재 모임 번호를 redis에 저장하고, 꺼내고 삭제하는 메소드
    public void setNowGrIdx(String key, int grIdx) {
        // 시간은 30분으로 제한
        redisTemplate.opsForValue().set(key, String.valueOf(grIdx));
    }

    // 현재 모임 번호를 redis에서 가져오는 메소드
    public int getNowGrIdx(String key) {
        // Redis에서 String 값 가져오기
        return Integer.parseInt((String) redisTemplate.opsForValue().get(key)); // String을 int로 변환
    }

    // 현재 모임 번호를 redis에서 삭제하는 메소드
    public void deleteNowGrIdx(String key) {
        redisTemplate.delete(key);
    }

    // 여행지 정보를 Redis에 저장하는 메서드
    public void setTravelInfo(String key, TravelDTO value) {
        // 객체를 Redis에 JSON 형식으로 저장
        // 객체 직렬화 후 저장
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key, json, 600, TimeUnit.SECONDS);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    // 여행지 정보를 Redis에서 가져오는 메서드
    public Object getTravelInfo(String key) {
        // "travelGroup:{grIdx}" 형식으로 저장된 값 가져오기        
        // ObjectMapper의 역직렬화로 저장된 json객체를 TravelDTO 객체로 변환
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = (String) redisTemplate.opsForValue().get(key);
            TravelDTO travelDTO = objectMapper.readValue(json, TravelDTO.class);            
            return travelDTO;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;

        // map 객체를 -> TravelDTO 객체로 변환
        // TravelDTO travelDTO = new TravelDTO();
        // travelDTO.setGrIdx((int) map.get("grIdx"));
        // travelDTO.setLsIdx((int) map.get("lsIdx"));
        // travelDTO.setTrCreatedAt((Date) map.get("trCreatedAt"));
        // travelDTO.setTrState((int) map.get("trState"));        
    }

    // 여행지 정보를 Redis에서 삭제하는 메서드    
    public void deleteTravelInfo(String key) {
        redisTemplate.delete(key);
    }
}
