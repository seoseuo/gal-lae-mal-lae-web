package com.wannago.mapper;

import com.wannago.dto.LocationDoDTO;
import com.wannago.entity.LocationDo;
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
public class LocationDoMapperImpl implements LocationDoMapper {

    @Override
    public LocationDoDTO toDTO(LocationDo locationDo) {
        if ( locationDo == null ) {
            return null;
        }

        LocationDoDTO locationDoDTO = new LocationDoDTO();

        locationDoDTO.setLdIdx( locationDo.getLdIdx() );
        locationDoDTO.setLdName( locationDo.getLdName() );

        return locationDoDTO;
    }

    @Override
    public LocationDo toEntity(LocationDoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        LocationDo.LocationDoBuilder locationDo = LocationDo.builder();

        locationDo.ldIdx( dto.getLdIdx() );
        locationDo.ldName( dto.getLdName() );

        return locationDo.build();
    }

    @Override
    public List<LocationDoDTO> toDTOList(List<LocationDo> locationDoList) {
        if ( locationDoList == null ) {
            return null;
        }

        List<LocationDoDTO> list = new ArrayList<LocationDoDTO>( locationDoList.size() );
        for ( LocationDo locationDo : locationDoList ) {
            list.add( toDTO( locationDo ) );
        }

        return list;
    }

    @Override
    public List<LocationDo> toEntityList(List<LocationDoDTO> locationDoDTOList) {
        if ( locationDoDTOList == null ) {
            return null;
        }

        List<LocationDo> list = new ArrayList<LocationDo>( locationDoDTOList.size() );
        for ( LocationDoDTO locationDoDTO : locationDoDTOList ) {
            list.add( toEntity( locationDoDTO ) );
        }

        return list;
    }
}
