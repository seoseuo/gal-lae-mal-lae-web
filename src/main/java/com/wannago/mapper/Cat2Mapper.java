package com.wannago.mapper;

import com.wannago.dto.Cat2DTO;
import com.wannago.entity.Cat2;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface Cat2Mapper {

    Cat2Mapper INSTANCE = Mappers.getMapper(Cat2Mapper.class);

    Cat2DTO toDTO(Cat2 cat2);
    Cat2 toEntity(Cat2DTO dto);

    List<Cat2DTO> toDTOList(List<Cat2> cat2List);
    List<Cat2> toEntityList(List<Cat2DTO> cat2DTOList);
}
