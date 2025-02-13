package com.wannago.mapper;

import com.wannago.dto.AlertsDTO;
import com.wannago.entity.Alerts;
import java.time.LocalDateTime;
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
public class AlertsMapperImpl implements AlertsMapper {

    @Override
    public AlertsDTO toDTO(Alerts alerts) {
        if ( alerts == null ) {
            return null;
        }

        AlertsDTO alertsDTO = new AlertsDTO();

        if ( alerts.getAlIdx() != null ) {
            alertsDTO.setAlIdx( alerts.getAlIdx() );
        }
        alertsDTO.setAlContent( alerts.getAlContent() );
        if ( alerts.getAlDate() != null ) {
            alertsDTO.setAlDate( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( alerts.getAlDate() ) );
        }
        if ( alerts.getAlRead() != null ) {
            alertsDTO.setAlRead( alerts.getAlRead() );
        }
        if ( alerts.getAlState() != null ) {
            alertsDTO.setAlState( alerts.getAlState() );
        }
        if ( alerts.getUsIdx() != null ) {
            alertsDTO.setUsIdx( alerts.getUsIdx() );
        }

        return alertsDTO;
    }

    @Override
    public Alerts toEntity(AlertsDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Alerts.AlertsBuilder alerts = Alerts.builder();

        alerts.alIdx( dto.getAlIdx() );
        alerts.alContent( dto.getAlContent() );
        if ( dto.getAlDate() != null ) {
            alerts.alDate( LocalDateTime.parse( dto.getAlDate() ) );
        }
        alerts.alRead( (byte) dto.getAlRead() );
        alerts.alState( dto.getAlState() );
        alerts.usIdx( dto.getUsIdx() );

        return alerts.build();
    }

    @Override
    public List<AlertsDTO> toDTOList(List<Alerts> alertsList) {
        if ( alertsList == null ) {
            return null;
        }

        List<AlertsDTO> list = new ArrayList<AlertsDTO>( alertsList.size() );
        for ( Alerts alerts : alertsList ) {
            list.add( toDTO( alerts ) );
        }

        return list;
    }

    @Override
    public List<Alerts> toEntityList(List<AlertsDTO> alertsDTOList) {
        if ( alertsDTOList == null ) {
            return null;
        }

        List<Alerts> list = new ArrayList<Alerts>( alertsDTOList.size() );
        for ( AlertsDTO alertsDTO : alertsDTOList ) {
            list.add( toEntity( alertsDTO ) );
        }

        return list;
    }
}
