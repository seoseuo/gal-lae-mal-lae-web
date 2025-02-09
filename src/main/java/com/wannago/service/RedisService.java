package com.wannago.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;



@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    //key값 code를 value로 하여 3분동안 저장한다.
    public void set(String key,String value,int seconds){
        ValueOperations<String, Object> valOperations = redisTemplate.opsForValue();
        //만료기간 3분
        valOperations.set(key,value,seconds, TimeUnit.SECONDS);
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
