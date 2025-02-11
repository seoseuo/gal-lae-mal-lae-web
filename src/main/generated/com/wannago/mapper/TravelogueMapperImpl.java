package com.wannago.mapper;

import com.wannago.dto.TravelogueDTO;
import com.wannago.entity.Travelogue;
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
public class TravelogueMapperImpl implements TravelogueMapper {

    @Override
    public TravelogueDTO toDTO(Travelogue travelogue) {
        if ( travelogue == null ) {
            return null;
        }

        TravelogueDTO travelogueDTO = new TravelogueDTO();

        if ( travelogue.getTlIdx() != null ) {
            travelogueDTO.setTlIdx( travelogue.getTlIdx() );
        }
        travelogueDTO.setTlTitle( travelogue.getTlTitle() );
        travelogueDTO.setTlContent( travelogue.getTlContent() );
        if ( travelogue.getTlState() != null ) {
            travelogueDTO.setTlState( travelogue.getTlState() );
        }
        if ( travelogue.getTrIdx() != null ) {
            travelogueDTO.setTrIdx( travelogue.getTrIdx() );
        }

        return travelogueDTO;
    }

    @Override
    public Travelogue toEntity(TravelogueDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Travelogue.TravelogueBuilder travelogue = Travelogue.builder();

        travelogue.tlIdx( dto.getTlIdx() );
        travelogue.tlTitle( dto.getTlTitle() );
        travelogue.tlContent( dto.getTlContent() );
        travelogue.tlState( dto.getTlState() );
        travelogue.trIdx( dto.getTrIdx() );

        return travelogue.build();
    }

    @Override
    public List<TravelogueDTO> toDTOList(List<Travelogue> travelogueList) {
        if ( travelogueList == null ) {
            return null;
        }

        List<TravelogueDTO> list = new ArrayList<TravelogueDTO>( travelogueList.size() );
        for ( Travelogue travelogue : travelogueList ) {
            list.add( toDTO( travelogue ) );
        }

        return list;
    }

    @Override
    public List<Travelogue> toEntityList(List<TravelogueDTO> travelogueDTOList) {
        if ( travelogueDTOList == null ) {
            return null;
        }

        List<Travelogue> list = new ArrayList<Travelogue>( travelogueDTOList.size() );
        for ( TravelogueDTO travelogueDTO : travelogueDTOList ) {
            list.add( toEntity( travelogueDTO ) );
        }

        return list;
    }
}
