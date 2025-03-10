package com.wannago.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.wannago.dto.BoardDTO;
import com.wannago.entity.Board;

@Mapper(componentModel = "spring")
public interface BoardMapper {

    BoardMapper INSTANCE = Mappers.getMapper(BoardMapper.class);

    BoardDTO toDTO(Board entity);
    Board toEntity(BoardDTO dto);
    List<BoardDTO> toDTOList(List<Board> entityList);
    List<Board> toEntityList(List<BoardDTO> dtoList);
}
