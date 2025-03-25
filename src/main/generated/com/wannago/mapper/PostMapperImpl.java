package com.wannago.mapper;

import com.wannago.dto.PostDTO;
import com.wannago.entity.Post;
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
public class PostMapperImpl implements PostMapper {

    @Override
    public PostDTO toDTO(Post post) {
        if ( post == null ) {
            return null;
        }

        PostDTO postDTO = new PostDTO();

        if ( post.getPoIdx() != null ) {
            postDTO.setPoIdx( post.getPoIdx() );
        }
        postDTO.setPoTitle( post.getPoTitle() );
        postDTO.setPoContent( post.getPoContent() );
        if ( post.getUsIdx() != null ) {
            postDTO.setUsIdx( post.getUsIdx() );
        }

        return postDTO;
    }

    @Override
    public Post toEntity(PostDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Post.PostBuilder post = Post.builder();

        post.poIdx( dto.getPoIdx() );
        post.poTitle( dto.getPoTitle() );
        post.poContent( dto.getPoContent() );
        post.usIdx( dto.getUsIdx() );

        return post.build();
    }

    @Override
    public List<PostDTO> toDTOList(List<Post> postList) {
        if ( postList == null ) {
            return null;
        }

        List<PostDTO> list = new ArrayList<PostDTO>( postList.size() );
        for ( Post post : postList ) {
            list.add( toDTO( post ) );
        }

        return list;
    }

    @Override
    public List<Post> toEntityList(List<PostDTO> postDTOList) {
        if ( postDTOList == null ) {
            return null;
        }

        List<Post> list = new ArrayList<Post>( postDTOList.size() );
        for ( PostDTO postDTO : postDTOList ) {
            list.add( toEntity( postDTO ) );
        }

        return list;
    }
}
