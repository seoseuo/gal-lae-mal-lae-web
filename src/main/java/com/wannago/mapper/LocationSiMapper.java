package com.wannago.mapper;

import com.wannago.dto.LocationSiDTO;
import com.wannago.entity.LocationSi;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LocationSiMapper {

    LocationSiMapper INSTANCE = Mappers.getMapper(LocationSiMapper.class);

    LocationSiDTO toDTO(LocationSi locationSi);
    LocationSi toEntity(LocationSiDTO dto);

    List<LocationSiDTO> toDTOList(List<LocationSi> locationSiList);
    List<LocationSi> toEntityList(List<LocationSiDTO> locationSiDTOList);
}
