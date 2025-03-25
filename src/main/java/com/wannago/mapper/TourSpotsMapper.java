package com.wannago.mapper;

import com.wannago.dto.TourSpotsDTO;
import com.wannago.entity.TourSpots;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TourSpotsMapper {

    TourSpotsMapper INSTANCE = Mappers.getMapper(TourSpotsMapper.class);
//
//    @Mapping(source = "cat1.cat1Title", target = "cat1")  // Cat1 객체의 cat1 필드를 String으로 매핑
//    @Mapping(source = "cat2.cat2Title", target = "cat2")  // Cat2, Cat3도 동일하게 적용
//    @Mapping(source = "cat3.cat3Title", target = "cat3")

    TourSpotsDTO toDTO(TourSpots tourSpots);
    TourSpots toEntity(TourSpotsDTO dto);

    List<TourSpotsDTO> toDTOList(List<TourSpots> tourSpotsList);
    List<TourSpots> toEntityList(List<TourSpotsDTO> tourSpotsDTOList);
}
