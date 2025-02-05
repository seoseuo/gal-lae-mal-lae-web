package com.wannago.mapper;

import com.wannago.dto.AlertsDTO;
import com.wannago.entity.Alerts;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AlertsMapper {

    AlertsMapper INSTANCE = Mappers.getMapper(AlertsMapper.class);

    AlertsDTO toDTO(Alerts alerts);
    Alerts toEntity(AlertsDTO dto);

    List<AlertsDTO> toDTOList(List<Alerts> alertsList);
    List<Alerts> toEntityList(List<AlertsDTO> alertsDTOList);
}
