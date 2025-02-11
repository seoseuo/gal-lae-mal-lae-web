package com.wannago.mapper;

import com.wannago.dto.Cat2DTO;
import com.wannago.entity.Cat2;
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
public class Cat2MapperImpl implements Cat2Mapper {

    @Override
    public Cat2DTO toDTO(Cat2 cat2) {
        if ( cat2 == null ) {
            return null;
        }

        Cat2DTO cat2DTO = new Cat2DTO();

        cat2DTO.setCat2( cat2.getCat2() );
        cat2DTO.setCat2Title( cat2.getCat2Title() );
        cat2DTO.setCat1( cat2.getCat1() );

        return cat2DTO;
    }

    @Override
    public Cat2 toEntity(Cat2DTO dto) {
        if ( dto == null ) {
            return null;
        }

        Cat2.Cat2Builder cat2 = Cat2.builder();

        cat2.cat2( dto.getCat2() );
        cat2.cat2Title( dto.getCat2Title() );
        cat2.cat1( dto.getCat1() );

        return cat2.build();
    }

    @Override
    public List<Cat2DTO> toDTOList(List<Cat2> cat2List) {
        if ( cat2List == null ) {
            return null;
        }

        List<Cat2DTO> list = new ArrayList<Cat2DTO>( cat2List.size() );
        for ( Cat2 cat2 : cat2List ) {
            list.add( toDTO( cat2 ) );
        }

        return list;
    }

    @Override
    public List<Cat2> toEntityList(List<Cat2DTO> cat2DTOList) {
        if ( cat2DTOList == null ) {
            return null;
        }

        List<Cat2> list = new ArrayList<Cat2>( cat2DTOList.size() );
        for ( Cat2DTO cat2DTO : cat2DTOList ) {
            list.add( toEntity( cat2DTO ) );
        }

        return list;
    }
}
