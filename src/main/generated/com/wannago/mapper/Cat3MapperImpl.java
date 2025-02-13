package com.wannago.mapper;

import com.wannago.dto.Cat3DTO;
import com.wannago.entity.Cat3;
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
public class Cat3MapperImpl implements Cat3Mapper {

    @Override
    public Cat3DTO toDTO(Cat3 cat3) {
        if ( cat3 == null ) {
            return null;
        }

        Cat3DTO cat3DTO = new Cat3DTO();

        cat3DTO.setCat3( cat3.getCat3() );
        cat3DTO.setCat3Title( cat3.getCat3Title() );
        cat3DTO.setCat2( cat3.getCat2() );

        return cat3DTO;
    }

    @Override
    public Cat3 toEntity(Cat3DTO dto) {
        if ( dto == null ) {
            return null;
        }

        Cat3.Cat3Builder cat3 = Cat3.builder();

        cat3.cat3( dto.getCat3() );
        cat3.cat3Title( dto.getCat3Title() );
        cat3.cat2( dto.getCat2() );

        return cat3.build();
    }

    @Override
    public List<Cat3DTO> toDTOList(List<Cat3> cat3List) {
        if ( cat3List == null ) {
            return null;
        }

        List<Cat3DTO> list = new ArrayList<Cat3DTO>( cat3List.size() );
        for ( Cat3 cat3 : cat3List ) {
            list.add( toDTO( cat3 ) );
        }

        return list;
    }

    @Override
    public List<Cat3> toEntityList(List<Cat3DTO> cat3DTOList) {
        if ( cat3DTOList == null ) {
            return null;
        }

        List<Cat3> list = new ArrayList<Cat3>( cat3DTOList.size() );
        for ( Cat3DTO cat3DTO : cat3DTOList ) {
            list.add( toEntity( cat3DTO ) );
        }

        return list;
    }
}
