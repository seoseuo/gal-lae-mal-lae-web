package com.wannago.mapper;

import com.wannago.dto.TravelGroupDTO;
import com.wannago.entity.TravelGroup;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TravelGroupMapper {

    TravelGroupMapper INSTANCE = Mappers.getMapper(TravelGroupMapper.class);

    TravelGroupDTO toDTO(TravelGroup travelGroup);
    TravelGroup toEntity(TravelGroupDTO dto);

    List<TravelGroupDTO> toDTOList(List<TravelGroup> travelGroupList);
    List<TravelGroup> toEntityList(List<TravelGroupDTO> travelGroupDTOList);
}
