package com.wannago.mapper;

import com.wannago.dto.ScheduleDTO;
import com.wannago.entity.Schedule;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-09T22:10:44+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.14 (Homebrew)"
)
@Component
public class ScheduleMapperImpl implements ScheduleMapper {

    @Override
    public ScheduleDTO toDTO(Schedule schedule) {
        if ( schedule == null ) {
            return null;
        }

        ScheduleDTO scheduleDTO = new ScheduleDTO();

        if ( schedule.getScIdx() != null ) {
            scheduleDTO.setScIdx( schedule.getScIdx() );
        }
        if ( schedule.getScDate() != null ) {
            scheduleDTO.setScDate( DateTimeFormatter.ISO_LOCAL_DATE.format( schedule.getScDate() ) );
        }
        if ( schedule.getScStartTime() != null ) {
            scheduleDTO.setScStartTime( DateTimeFormatter.ISO_LOCAL_TIME.format( schedule.getScStartTime() ) );
        }
        if ( schedule.getScEndTime() != null ) {
            scheduleDTO.setScEndTime( DateTimeFormatter.ISO_LOCAL_TIME.format( schedule.getScEndTime() ) );
        }
        if ( schedule.getTrIdx() != null ) {
            scheduleDTO.setTrIdx( schedule.getTrIdx() );
        }
        if ( schedule.getContentid() != null ) {
            scheduleDTO.setContentid( schedule.getContentid() );
        }

        return scheduleDTO;
    }

    @Override
    public Schedule toEntity(ScheduleDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Schedule.ScheduleBuilder schedule = Schedule.builder();

        schedule.scIdx( dto.getScIdx() );
        if ( dto.getScDate() != null ) {
            schedule.scDate( LocalDate.parse( dto.getScDate() ) );
        }
        if ( dto.getScStartTime() != null ) {
            schedule.scStartTime( LocalTime.parse( dto.getScStartTime() ) );
        }
        if ( dto.getScEndTime() != null ) {
            schedule.scEndTime( LocalTime.parse( dto.getScEndTime() ) );
        }
        schedule.trIdx( dto.getTrIdx() );
        schedule.contentid( dto.getContentid() );

        return schedule.build();
    }

    @Override
    public List<ScheduleDTO> toDTOList(List<Schedule> scheduleList) {
        if ( scheduleList == null ) {
            return null;
        }

        List<ScheduleDTO> list = new ArrayList<ScheduleDTO>( scheduleList.size() );
        for ( Schedule schedule : scheduleList ) {
            list.add( toDTO( schedule ) );
        }

        return list;
    }

    @Override
    public List<Schedule> toEntityList(List<ScheduleDTO> scheduleDTOList) {
        if ( scheduleDTOList == null ) {
            return null;
        }

        List<Schedule> list = new ArrayList<Schedule>( scheduleDTOList.size() );
        for ( ScheduleDTO scheduleDTO : scheduleDTOList ) {
            list.add( toEntity( scheduleDTO ) );
        }

        return list;
    }
}
