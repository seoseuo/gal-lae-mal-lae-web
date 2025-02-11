package com.wannago.mapper;

import com.wannago.dto.TourSpotsDTO;
import com.wannago.entity.TourSpots;
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
public class TourSpotsMapperImpl implements TourSpotsMapper {

    @Override
    public TourSpotsDTO toDTO(TourSpots tourSpots) {
        if ( tourSpots == null ) {
            return null;
        }

        TourSpotsDTO tourSpotsDTO = new TourSpotsDTO();

        if ( tourSpots.getContentid() != null ) {
            tourSpotsDTO.setContentid( tourSpots.getContentid() );
        }
        tourSpotsDTO.setTitle( tourSpots.getTitle() );
        tourSpotsDTO.setFirstimage( tourSpots.getFirstimage() );
        tourSpotsDTO.setFirstimage2( tourSpots.getFirstimage2() );
        tourSpotsDTO.setMapx( tourSpots.getMapx() );
        tourSpotsDTO.setMapy( tourSpots.getMapy() );
        if ( tourSpots.getMlevel() != null ) {
            tourSpotsDTO.setMlevel( tourSpots.getMlevel() );
        }
        tourSpotsDTO.setAddr1( tourSpots.getAddr1() );
        tourSpotsDTO.setAddr2( tourSpots.getAddr2() );
        tourSpotsDTO.setZipcode( tourSpots.getZipcode() );
        tourSpotsDTO.setTel( tourSpots.getTel() );
        tourSpotsDTO.setContenttypeid( tourSpots.getContenttypeid() );
        tourSpotsDTO.setBooktour( tourSpots.getBooktour() );
        tourSpotsDTO.setCpyrhtDivCd( tourSpots.getCpyrhtDivCd() );
        tourSpotsDTO.setCreatedtime( tourSpots.getCreatedtime() );
        tourSpotsDTO.setModifiedtime( tourSpots.getModifiedtime() );
        if ( tourSpots.getLdIdx() != null ) {
            tourSpotsDTO.setLdIdx( tourSpots.getLdIdx() );
        }
        if ( tourSpots.getLsIdx() != null ) {
            tourSpotsDTO.setLsIdx( tourSpots.getLsIdx() );
        }
        tourSpotsDTO.setCat1( tourSpots.getCat1() );
        tourSpotsDTO.setCat2( tourSpots.getCat2() );
        tourSpotsDTO.setCat3( tourSpots.getCat3() );

        return tourSpotsDTO;
    }

    @Override
    public TourSpots toEntity(TourSpotsDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TourSpots.TourSpotsBuilder tourSpots = TourSpots.builder();

        tourSpots.contentid( dto.getContentid() );
        tourSpots.title( dto.getTitle() );
        tourSpots.firstimage( dto.getFirstimage() );
        tourSpots.firstimage2( dto.getFirstimage2() );
        tourSpots.mapx( dto.getMapx() );
        tourSpots.mapy( dto.getMapy() );
        tourSpots.mlevel( (byte) dto.getMlevel() );
        tourSpots.addr1( dto.getAddr1() );
        tourSpots.addr2( dto.getAddr2() );
        tourSpots.zipcode( dto.getZipcode() );
        tourSpots.tel( dto.getTel() );
        tourSpots.contenttypeid( dto.getContenttypeid() );
        tourSpots.booktour( dto.getBooktour() );
        tourSpots.cpyrhtDivCd( dto.getCpyrhtDivCd() );
        tourSpots.createdtime( dto.getCreatedtime() );
        tourSpots.modifiedtime( dto.getModifiedtime() );
        tourSpots.ldIdx( dto.getLdIdx() );
        tourSpots.lsIdx( dto.getLsIdx() );
        tourSpots.cat1( dto.getCat1() );
        tourSpots.cat2( dto.getCat2() );
        tourSpots.cat3( dto.getCat3() );

        return tourSpots.build();
    }

    @Override
    public List<TourSpotsDTO> toDTOList(List<TourSpots> tourSpotsList) {
        if ( tourSpotsList == null ) {
            return null;
        }

        List<TourSpotsDTO> list = new ArrayList<TourSpotsDTO>( tourSpotsList.size() );
        for ( TourSpots tourSpots : tourSpotsList ) {
            list.add( toDTO( tourSpots ) );
        }

        return list;
    }

    @Override
    public List<TourSpots> toEntityList(List<TourSpotsDTO> tourSpotsDTOList) {
        if ( tourSpotsDTOList == null ) {
            return null;
        }

        List<TourSpots> list = new ArrayList<TourSpots>( tourSpotsDTOList.size() );
        for ( TourSpotsDTO tourSpotsDTO : tourSpotsDTOList ) {
            list.add( toEntity( tourSpotsDTO ) );
        }

        return list;
    }
}
