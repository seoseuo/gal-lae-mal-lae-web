package com.wannago.mapper;

import com.wannago.dto.ScheduleDTO;
import com.wannago.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);

    ScheduleDTO toDTO(Schedule schedule);
    Schedule toEntity(ScheduleDTO dto);

    List<ScheduleDTO> toDTOList(List<Schedule> scheduleList);
    List<Schedule> toEntityList(List<ScheduleDTO> scheduleDTOList);
}
