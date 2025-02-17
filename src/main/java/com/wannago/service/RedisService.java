package com.wannago.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wannago.dto.MessageDTO;



@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    //key값 code를 value로 하여 3분동안 저장한다.



    
    public void set(String key,String value,int seconds){
        ValueOperations<String, Object> valOperations = redisTemplate.opsForValue();
        valOperations.set(key,value,seconds, TimeUnit.SECONDS);
    }


    public void saveChatMessage(String key,MessageDTO value,int days){
        try {
            String jsonValue = objectMapper.writeValueAsString(value);
            redisTemplate.opsForList().rightPush(key, jsonValue);
            redisTemplate.opsForList().trim(key, -100, -1);
            redisTemplate.expire(key, days, TimeUnit.DAYS);
        } catch (Exception e) {
            throw new RuntimeException("Redis 저장 실패", e);
        }
    }
    public void setChatMessageList(String key,List<MessageDTO> value,int days){
        try {
            for(MessageDTO messageDTO : value){
                String jsonValue = objectMapper.writeValueAsString(messageDTO);
                redisTemplate.opsForList().rightPush(key, jsonValue);
            }
            redisTemplate.expire(key, days, TimeUnit.DAYS);
        } catch (Exception e) {
            throw new RuntimeException("Redis 저장 실패", e);
        }
    }
    
    public List<MessageDTO> getChatMessage(String key){
        List<Object> messages = redisTemplate.opsForList().range(key, 0, -1);
        List<MessageDTO> messageDTOs = new ArrayList<>();
        for (Object message : messages) {
            try {
                MessageDTO messageDTO = objectMapper.readValue(message.toString(), MessageDTO.class);
                messageDTOs.add(messageDTO);
            } catch (Exception e) {
                throw new RuntimeException("Redis 데이터 변환 실패", e);
            }
        }
        return messageDTOs;
    }
    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }
    //key값인 email에 있는 value를 가져온다.
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
    //key값인 email에 있는 value를 삭제한다.
    public void delete(String key){
        redisTemplate.delete(key);
    }
}
