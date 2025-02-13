package com.wannago.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.json.JSONObject;
import org.json.JSONArray;
import com.wannago.util.ApiCall;
import com.wannago.dto.Cat1DTO;
import com.wannago.dto.Cat2DTO;
import com.wannago.dto.Cat3DTO;
import com.wannago.dto.LocationDoDTO;
import com.wannago.dto.LocationSiDTO;
import com.wannago.dto.ResponseDTO;
import com.wannago.dto.TourSpotsDTO;
import com.wannago.repository.Cat1Repository;
import com.wannago.repository.Cat2Repository;
import com.wannago.repository.Cat3Repository;
import com.wannago.repository.LocationDoRepository;
import com.wannago.repository.LocationSiRepository;
import com.wannago.repository.TourSpotsRepository;
import com.wannago.entity.Cat1;
import com.wannago.entity.Cat2;
import com.wannago.entity.Cat3;
import com.wannago.entity.LocationDo;
import com.wannago.entity.LocationSi;
import com.wannago.entity.TourSpots;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import com.wannago.mapper.Cat1Mapper;
import com.wannago.mapper.Cat2Mapper;
import com.wannago.mapper.Cat3Mapper;
import com.wannago.mapper.LocationDoMapper;
import com.wannago.mapper.LocationSiMapper;
import com.wannago.mapper.TourSpotsMapper;
@Service
public class OpenApiService {
    @Autowired
    private ApiCall apiCall;

    @Autowired
    private Cat1Repository cat1Repository;
    @Autowired
    private Cat2Repository cat2Repository;
    @Autowired
    private Cat3Repository cat3Repository;
    @Autowired
    private Cat1Mapper cat1Mapper;
    @Autowired
    private Cat2Mapper cat2Mapper;
    @Autowired
    private Cat3Mapper cat3Mapper;
    @Autowired
    private LocationDoMapper locationDoMapper;
    @Autowired
    private LocationSiMapper locationSiMapper;
    @Autowired
    private LocationDoRepository locationDoRepository;
    @Autowired
    private LocationSiRepository locationSiRepository;
    @Autowired
    private TourSpotsMapper tourSpotMapper;
    @Autowired
    private TourSpotsRepository tourSpotRepository;
    public ResponseDTO saveCateGory() {
        List<Cat1DTO> cat1DTOList = apiCall.getCat1List();
        List<Cat2DTO> cat2DTOList = new ArrayList<>();
        List<Cat3DTO> cat3DTOList = new ArrayList<>();
        for (Cat1DTO cat1DTO : cat1DTOList) {
            cat2DTOList.addAll(apiCall.getCat2List(cat1DTO.getC1Code()));
        }
        for (Cat2DTO cat2DTO : cat2DTOList) {
            cat3DTOList.addAll(apiCall.getCat3List(cat2DTO.getC1Code(), cat2DTO.getC2Code()));
        }
        List<Cat1> cat1List = cat1Mapper.toEntityList(cat1DTOList);
        List<Cat2> cat2List = cat2Mapper.toEntityList(cat2DTOList);
        List<Cat3> cat3List = cat3Mapper.toEntityList(cat3DTOList);
        cat1Repository.saveAll(cat1List);
        cat2Repository.saveAll(cat2List);
        cat3Repository.saveAll(cat3List);
        return new ResponseDTO(true, "카테고리 저장 성공");
    }
    public ResponseDTO saveAreaCode() {
        List<LocationDoDTO> locationDoDTOList = apiCall.getLocationList();
        List<LocationSiDTO> locationSiDTOList = new ArrayList<>();
        for (LocationDoDTO locationDoDTO : locationDoDTOList) {
            locationSiDTOList.addAll(apiCall.getLocationSiList(locationDoDTO.getLdIdx()));
        }
        List<LocationDo> locationDoList = locationDoMapper.toEntityList(locationDoDTOList);
        List<LocationSi> locationSiList = locationSiMapper.toEntityList(locationSiDTOList);
        locationDoRepository.saveAll(locationDoList);
        locationSiRepository.saveAll(locationSiList);
        return new ResponseDTO(true, "지역 코드 저장 성공");
    }
    public ResponseDTO saveTourSpot() {
        // 먼저 지역 정보가 저장되어 있는지 확인
        if (locationDoRepository.count() == 0 || locationSiRepository.count() == 0) {
            // 지역 정보가 없으면 먼저 저장
            saveAreaCode();
        }
        
        List<TourSpotsDTO> tourSpotDTOList = apiCall.getTourSpotList(1);
        List<TourSpots> tourSpotList = tourSpotMapper.toEntityList(tourSpotDTOList);
        tourSpotRepository.saveAll(tourSpotList);
        return new ResponseDTO(true, "관광지 저장 성공");
    }
}
