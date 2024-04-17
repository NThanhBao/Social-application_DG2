package com.Social.application.DG2.mapper;

import com.Social.application.DG2.dto.PostsDto;
import com.Social.application.DG2.entity.Posts;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostsMapper {
    @Autowired
    private ModelMapper modelMapper;

    public PostsDto mapToDto(Posts posts) {
        return modelMapper.map(posts, PostsDto.class);
    }

    public Posts mapToEntity(PostsDto postsDto) {
        return modelMapper.map(postsDto, Posts.class);
    }
}
