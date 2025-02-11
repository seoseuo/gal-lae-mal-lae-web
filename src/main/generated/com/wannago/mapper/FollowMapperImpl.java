package com.wannago.mapper;

import com.wannago.dto.FollowDTO;
import com.wannago.entity.Follow;
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
public class FollowMapperImpl implements FollowMapper {

    @Override
    public FollowDTO toDTO(Follow follow) {
        if ( follow == null ) {
            return null;
        }

        FollowDTO followDTO = new FollowDTO();

        if ( follow.getFoIdx() != null ) {
            followDTO.setFoIdx( follow.getFoIdx() );
        }
        if ( follow.getFollower() != null ) {
            followDTO.setFollower( follow.getFollower() );
        }
        if ( follow.getFollowee() != null ) {
            followDTO.setFollowee( follow.getFollowee() );
        }

        return followDTO;
    }

    @Override
    public Follow toEntity(FollowDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Follow.FollowBuilder follow = Follow.builder();

        follow.foIdx( dto.getFoIdx() );
        follow.follower( dto.getFollower() );
        follow.followee( dto.getFollowee() );

        return follow.build();
    }

    @Override
    public List<FollowDTO> toDTOList(List<Follow> followList) {
        if ( followList == null ) {
            return null;
        }

        List<FollowDTO> list = new ArrayList<FollowDTO>( followList.size() );
        for ( Follow follow : followList ) {
            list.add( toDTO( follow ) );
        }

        return list;
    }

    @Override
    public List<Follow> toEntityList(List<FollowDTO> followDTOList) {
        if ( followDTOList == null ) {
            return null;
        }

        List<Follow> list = new ArrayList<Follow>( followDTOList.size() );
        for ( FollowDTO followDTO : followDTOList ) {
            list.add( toEntity( followDTO ) );
        }

        return list;
    }
}
