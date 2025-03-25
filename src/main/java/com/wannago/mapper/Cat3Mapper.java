package com.wannago.mapper;

import com.wannago.dto.Cat3DTO;
import com.wannago.entity.Cat3;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface Cat3Mapper {

    Cat3Mapper INSTANCE = Mappers.getMapper(Cat3Mapper.class);

    Cat3DTO toDTO(Cat3 cat3);
    Cat3 toEntity(Cat3DTO dto);

    List<Cat3DTO> toDTOList(List<Cat3> cat3List);
    List<Cat3> toEntityList(List<Cat3DTO> cat3DTOList);
}
