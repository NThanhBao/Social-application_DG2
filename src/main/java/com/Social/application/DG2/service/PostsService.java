package com.Social.application.DG2.service;

import com.Social.application.DG2.dto.PostsDto;
import com.Social.application.DG2.entity.Posts;
import com.Social.application.DG2.entity.Users;

import java.util.List;
import java.util.UUID;

public interface PostsService {
    Posts createPosts(PostsDto post);
    List<Posts> getPostsByUserId(UUID userId);
    List<Posts> getAllPosts();
    //    String updatePosts(UUID postId, PostsDto postDto);
    void deletePost(UUID postId);
}
