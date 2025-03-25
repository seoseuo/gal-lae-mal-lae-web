package com.wannago.mapper;

import com.wannago.dto.TravelogueDTO;
import com.wannago.entity.Travelogue;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TravelogueMapper {

    TravelogueMapper INSTANCE = Mappers.getMapper(TravelogueMapper.class);

    TravelogueDTO toDTO(Travelogue travelogue);
    Travelogue toEntity(TravelogueDTO dto);

    List<TravelogueDTO> toDTOList(List<Travelogue> travelogueList);
    List<Travelogue> toEntityList(List<TravelogueDTO> travelogueDTOList);
}
