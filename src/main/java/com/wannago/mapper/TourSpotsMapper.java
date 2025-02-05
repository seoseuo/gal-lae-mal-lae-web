package com.wannago.mapper;

import com.wannago.dto.TourSpotsDTO;
import com.wannago.entity.TourSpots;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TourSpotsMapper {

    TourSpotsMapper INSTANCE = Mappers.getMapper(TourSpotsMapper.class);

    TourSpotsDTO toDTO(TourSpots tourSpots);
    TourSpots toEntity(TourSpotsDTO dto);

    List<TourSpotsDTO> toDTOList(List<TourSpots> tourSpotsList);
    List<TourSpots> toEntityList(List<TourSpotsDTO> tourSpotsDTOList);
}
