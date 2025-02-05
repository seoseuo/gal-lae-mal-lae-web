package com.wannago.mapper;

import com.wannago.dto.PostDTO;
import com.wannago.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    PostDTO toDTO(Post post);
    Post toEntity(PostDTO dto);

    List<PostDTO> toDTOList(List<Post> postList);
    List<Post> toEntityList(List<PostDTO> postDTOList);
}
