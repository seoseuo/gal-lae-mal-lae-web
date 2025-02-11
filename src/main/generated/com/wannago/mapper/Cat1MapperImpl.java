package com.wannago.mapper;

import com.wannago.dto.Cat1DTO;
import com.wannago.entity.Cat1;
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
public class Cat1MapperImpl implements Cat1Mapper {

    @Override
    public Cat1DTO toDTO(Cat1 cat1) {
        if ( cat1 == null ) {
            return null;
        }

        Cat1DTO cat1DTO = new Cat1DTO();

        cat1DTO.setCat1( cat1.getCat1() );
        cat1DTO.setCat1Title( cat1.getCat1Title() );

        return cat1DTO;
    }

    @Override
    public Cat1 toEntity(Cat1DTO dto) {
        if ( dto == null ) {
            return null;
        }

        Cat1.Cat1Builder cat1 = Cat1.builder();

        cat1.cat1( dto.getCat1() );
        cat1.cat1Title( dto.getCat1Title() );

        return cat1.build();
    }

    @Override
    public List<Cat1DTO> toDTOList(List<Cat1> cat1List) {
        if ( cat1List == null ) {
            return null;
        }

        List<Cat1DTO> list = new ArrayList<Cat1DTO>( cat1List.size() );
        for ( Cat1 cat1 : cat1List ) {
            list.add( toDTO( cat1 ) );
        }

        return list;
    }

    @Override
    public List<Cat1> toEntityList(List<Cat1DTO> cat1DTOList) {
        if ( cat1DTOList == null ) {
            return null;
        }

        List<Cat1> list = new ArrayList<Cat1>( cat1DTOList.size() );
        for ( Cat1DTO cat1DTO : cat1DTOList ) {
            list.add( toEntity( cat1DTO ) );
        }

        return list;
    }
}
