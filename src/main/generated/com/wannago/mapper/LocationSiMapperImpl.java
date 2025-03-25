package com.wannago.mapper;

import com.wannago.dto.LocationSiDTO;
import com.wannago.entity.LocationSi;
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
public class LocationSiMapperImpl implements LocationSiMapper {

    @Override
    public LocationSiDTO toDTO(LocationSi locationSi) {
        if ( locationSi == null ) {
            return null;
        }

        LocationSiDTO locationSiDTO = new LocationSiDTO();

        locationSiDTO.setLsIdx( locationSi.getLsIdx() );
        locationSiDTO.setLsName( locationSi.getLsName() );
        if ( locationSi.getLdIdx() != null ) {
            locationSiDTO.setLdIdx( locationSi.getLdIdx() );
        }

        return locationSiDTO;
    }

    @Override
    public LocationSi toEntity(LocationSiDTO dto) {
        if ( dto == null ) {
            return null;
        }

        LocationSi.LocationSiBuilder locationSi = LocationSi.builder();

        locationSi.lsIdx( dto.getLsIdx() );
        locationSi.lsName( dto.getLsName() );
        locationSi.ldIdx( dto.getLdIdx() );

        return locationSi.build();
    }

    @Override
    public List<LocationSiDTO> toDTOList(List<LocationSi> locationSiList) {
        if ( locationSiList == null ) {
            return null;
        }

        List<LocationSiDTO> list = new ArrayList<LocationSiDTO>( locationSiList.size() );
        for ( LocationSi locationSi : locationSiList ) {
            list.add( toDTO( locationSi ) );
        }

        return list;
    }

    @Override
    public List<LocationSi> toEntityList(List<LocationSiDTO> locationSiDTOList) {
        if ( locationSiDTOList == null ) {
            return null;
        }

        List<LocationSi> list = new ArrayList<LocationSi>( locationSiDTOList.size() );
        for ( LocationSiDTO locationSiDTO : locationSiDTOList ) {
            list.add( toEntity( locationSiDTO ) );
        }

        return list;
    }
}
