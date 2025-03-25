package com.wannago.mapper;

import com.wannago.dto.UserDTO;
import com.wannago.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User user);
    User toEntity(UserDTO dto);

    List<UserDTO> toDTOList(List<User> userList);
    List<User> toEntityList(List<UserDTO> userDTOList);
}
