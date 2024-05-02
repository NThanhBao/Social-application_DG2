package com.Social.application.DG2.service;

import com.Social.application.DG2.dto.CommentsDto;
import com.Social.application.DG2.entity.Comments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CommentsService {
    Comments saveComment(CommentsDto comment);
    Page<Comments> getCommentsByPostId(String postId, Pageable pageable);
    void deleteComment(UUID commentId);
    void updateComments(CommentsDto commentsDto);
}
