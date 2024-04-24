package com.Social.application.DG2.service;

import com.Social.application.DG2.entity.Comments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CommentsService {
    Comments saveComment(Comments comment);
    Comments postCommentById(UUID commentId);
    Page<Comments> getCommentsByPostId(UUID postId, Pageable pageable);
    void deleteComment(UUID commentId);
    String updateComments();
}
