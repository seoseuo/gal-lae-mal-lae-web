package com.wannago.mapper;

import com.wannago.dto.TravelDTO;
import com.wannago.entity.Travel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TravelMapper {

    TravelMapper INSTANCE = Mappers.getMapper(TravelMapper.class);

    TravelDTO toDTO(Travel travel);
    Travel toEntity(TravelDTO dto);

    List<TravelDTO> toDTOList(List<Travel> travelList);
    List<Travel> toEntityList(List<TravelDTO> travelDTOList);
}
