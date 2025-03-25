package com.wannago.mapper;

import com.wannago.dto.FollowDTO;
import com.wannago.entity.Follow;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FollowMapper {

    FollowMapper INSTANCE = Mappers.getMapper(FollowMapper.class);

    FollowDTO toDTO(Follow follow);
    Follow toEntity(FollowDTO dto);

    List<FollowDTO> toDTOList(List<Follow> followList);
    List<Follow> toEntityList(List<FollowDTO> followDTOList);
}
