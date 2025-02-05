package com.wannago.mapper;

import com.wannago.dto.LocationDoDTO;
import com.wannago.entity.LocationDo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LocationDoMapper {

    LocationDoMapper INSTANCE = Mappers.getMapper(LocationDoMapper.class);

    LocationDoDTO toDTO(LocationDo locationDo);
    LocationDo toEntity(LocationDoDTO dto);

    List<LocationDoDTO> toDTOList(List<LocationDo> locationDoList);
    List<LocationDo> toEntityList(List<LocationDoDTO> locationDoDTOList);
}
