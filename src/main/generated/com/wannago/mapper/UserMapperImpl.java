package com.wannago.mapper;

import com.wannago.dto.UserDTO;
import com.wannago.entity.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-09T22:10:44+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.14 (Homebrew)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setUsIdx( user.getUsIdx() );
        userDTO.setUsEmail( user.getUsEmail() );
        userDTO.setUsName( user.getUsName() );
        userDTO.setUsPw( user.getUsPw() );
        userDTO.setUsProfile( user.getUsProfile() );
        if ( user.getUsJoinDate() != null ) {
            userDTO.setUsJoinDate( new SimpleDateFormat().format( user.getUsJoinDate() ) );
        }
        if ( user.getUsLeaveDate() != null ) {
            userDTO.setUsLeaveDate( new SimpleDateFormat().format( user.getUsLeaveDate() ) );
        }
        userDTO.setUsState( user.getUsState() );

        return userDTO;
    }

    @Override
    public User toEntity(UserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.usIdx( dto.getUsIdx() );
        user.usName( dto.getUsName() );
        user.usProfile( dto.getUsProfile() );
        user.usEmail( dto.getUsEmail() );
        user.usPw( dto.getUsPw() );
        try {
            if ( dto.getUsJoinDate() != null ) {
                user.usJoinDate( new SimpleDateFormat().parse( dto.getUsJoinDate() ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( dto.getUsLeaveDate() != null ) {
                user.usLeaveDate( new SimpleDateFormat().parse( dto.getUsLeaveDate() ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        user.usState( dto.getUsState() );

        return user.build();
    }

    @Override
    public List<UserDTO> toDTOList(List<User> userList) {
        if ( userList == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( userList.size() );
        for ( User user : userList ) {
            list.add( toDTO( user ) );
        }

        return list;
    }

    @Override
    public List<User> toEntityList(List<UserDTO> userDTOList) {
        if ( userDTOList == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( userDTOList.size() );
        for ( UserDTO userDTO : userDTOList ) {
            list.add( toEntity( userDTO ) );
        }

        return list;
    }
}
