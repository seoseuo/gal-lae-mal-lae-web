package com.wannago.mapper;

import com.wannago.dto.TravelDTO;
import com.wannago.entity.Travel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class TravelMapperImpl implements TravelMapper {

    @Override
    public TravelDTO toDTO(Travel travel) {
        if ( travel == null ) {
            return null;
        }

        TravelDTO travelDTO = new TravelDTO();

        travelDTO.setTrIdx( travel.getTrIdx() );
        if ( travel.getTrStartTime() != null ) {
            travelDTO.setTrStartTime( new SimpleDateFormat().format( travel.getTrStartTime() ) );
        }
        if ( travel.getTrEndTime() != null ) {
            travelDTO.setTrEndTime( new SimpleDateFormat().format( travel.getTrEndTime() ) );
        }
        travelDTO.setLsIdx( travel.getLsIdx() );
        if ( travel.getTrCreatedAt() != null ) {
            travelDTO.setTrCreatedAt( new SimpleDateFormat().format( travel.getTrCreatedAt() ) );
        }

        return travelDTO;
    }

    @Override
    public Travel toEntity(TravelDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Travel.TravelBuilder travel = Travel.builder();

        travel.trIdx( dto.getTrIdx() );
        try {
            if ( dto.getTrStartTime() != null ) {
                travel.trStartTime( new SimpleDateFormat().parse( dto.getTrStartTime() ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( dto.getTrEndTime() != null ) {
                travel.trEndTime( new SimpleDateFormat().parse( dto.getTrEndTime() ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        travel.lsIdx( dto.getLsIdx() );
        try {
            if ( dto.getTrCreatedAt() != null ) {
                travel.trCreatedAt( new SimpleDateFormat().parse( dto.getTrCreatedAt() ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }

        return travel.build();
    }

    @Override
    public List<TravelDTO> toDTOList(List<Travel> travelList) {
        if ( travelList == null ) {
            return null;
        }

        List<TravelDTO> list = new ArrayList<TravelDTO>( travelList.size() );
        for ( Travel travel : travelList ) {
            list.add( toDTO( travel ) );
        }

        return list;
    }

    @Override
    public List<Travel> toEntityList(List<TravelDTO> travelDTOList) {
        if ( travelDTOList == null ) {
            return null;
        }

        List<Travel> list = new ArrayList<Travel>( travelDTOList.size() );
        for ( TravelDTO travelDTO : travelDTOList ) {
            list.add( toEntity( travelDTO ) );
        }

        return list;
    }
}
