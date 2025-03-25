package com.wannago.mapper;

import com.wannago.dto.MemberDTO;
import com.wannago.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    MemberDTO toDTO(Member member);
    Member toEntity(MemberDTO memberDTO);

    List<MemberDTO> toDTOList(List<Member> memberList);
    List<Member> toEntityList(List<MemberDTO> memberDTOList);
}
