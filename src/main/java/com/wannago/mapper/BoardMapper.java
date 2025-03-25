package com.wannago.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import com.wannago.dto.BoardDTO;
import com.wannago.entity.Board;

@Mapper(componentModel = "spring")
public interface BoardMapper {

    BoardMapper INSTANCE = Mappers.getMapper(BoardMapper.class);

    @Mapping(source = "user.usName", target = "usName")
    @Mapping(source = "user.usIdx", target = "usIdx")
    @Mapping(source = "user.usProfile", target = "usProfile")
    BoardDTO toDTO(Board entity);
    @Mapping(source = "usName", target = "user.usName")
    @Mapping(source = "usIdx", target = "user.usIdx")
    @Mapping(source = "usProfile", target = "user.usProfile")
    Board toEntity(BoardDTO dto);
    List<BoardDTO> toDTOList(List<Board> entityList);
    List<Board> toEntityList(List<BoardDTO> dtoList);
}
