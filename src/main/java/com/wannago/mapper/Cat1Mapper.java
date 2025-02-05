package com.wannago.mapper;

import com.wannago.dto.Cat1DTO;
import com.wannago.entity.Cat1;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface Cat1Mapper {

    Cat1Mapper INSTANCE = Mappers.getMapper(Cat1Mapper.class);

    Cat1DTO toDTO(Cat1 cat1);
    Cat1 toEntity(Cat1DTO dto);

    List<Cat1DTO> toDTOList(List<Cat1> cat1List);
    List<Cat1> toEntityList(List<Cat1DTO> cat1DTOList);
}
