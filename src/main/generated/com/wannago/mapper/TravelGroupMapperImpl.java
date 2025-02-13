package com.wannago.mapper;

import com.wannago.dto.TravelGroupDTO;
import com.wannago.entity.TravelGroup;
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
public class TravelGroupMapperImpl implements TravelGroupMapper {

    @Override
    public TravelGroupDTO toDTO(TravelGroup travelGroup) {
        if ( travelGroup == null ) {
            return null;
        }

        TravelGroupDTO travelGroupDTO = new TravelGroupDTO();

        travelGroupDTO.setGrIdx( travelGroup.getGrIdx() );
        travelGroupDTO.setGrName( travelGroup.getGrName() );
        travelGroupDTO.setGrState( travelGroup.getGrState() );
        if ( travelGroup.getGrCreatedAt() != null ) {
            travelGroupDTO.setGrCreatedAt( new SimpleDateFormat().format( travelGroup.getGrCreatedAt() ) );
        }
        if ( travelGroup.getGrDeletedAt() != null ) {
            travelGroupDTO.setGrDeletedAt( new SimpleDateFormat().format( travelGroup.getGrDeletedAt() ) );
        }

        return travelGroupDTO;
    }

    @Override
    public TravelGroup toEntity(TravelGroupDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TravelGroup.TravelGroupBuilder travelGroup = TravelGroup.builder();

        travelGroup.grIdx( dto.getGrIdx() );
        travelGroup.grName( dto.getGrName() );
        travelGroup.grState( dto.getGrState() );
        try {
            if ( dto.getGrCreatedAt() != null ) {
                travelGroup.grCreatedAt( new SimpleDateFormat().parse( dto.getGrCreatedAt() ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( dto.getGrDeletedAt() != null ) {
                travelGroup.grDeletedAt( new SimpleDateFormat().parse( dto.getGrDeletedAt() ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }

        return travelGroup.build();
    }

    @Override
    public List<TravelGroupDTO> toDTOList(List<TravelGroup> travelGroupList) {
        if ( travelGroupList == null ) {
            return null;
        }

        List<TravelGroupDTO> list = new ArrayList<TravelGroupDTO>( travelGroupList.size() );
        for ( TravelGroup travelGroup : travelGroupList ) {
            list.add( toDTO( travelGroup ) );
        }

        return list;
    }

    @Override
    public List<TravelGroup> toEntityList(List<TravelGroupDTO> travelGroupDTOList) {
        if ( travelGroupDTOList == null ) {
            return null;
        }

        List<TravelGroup> list = new ArrayList<TravelGroup>( travelGroupDTOList.size() );
        for ( TravelGroupDTO travelGroupDTO : travelGroupDTOList ) {
            list.add( toEntity( travelGroupDTO ) );
        }

        return list;
    }
}
