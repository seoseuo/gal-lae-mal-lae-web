package com.wannago.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.wannago.dto.TravelogueLikeDTO;
import com.wannago.entity.TravelogueLike;

@Mapper(componentModel = "spring")
public interface TravelogueLikeMapper {
    TravelogueLikeDTO toDTO(TravelogueLike travelogueLike);
    List<TravelogueLikeDTO> toDTOList(List<TravelogueLike> travelogueLikeList);
}
