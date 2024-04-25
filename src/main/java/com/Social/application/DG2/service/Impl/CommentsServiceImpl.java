package com.Social.application.DG2.service.Impl;

import com.Social.application.DG2.entity.Comments;
import com.Social.application.DG2.service.CommentsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommentsServiceImpl implements CommentsService {
    @Override
    public Comments saveComment(Comments comment) {
        return null;
    }

    @Override
    public Comments postCommentById(UUID commentId) {
        return null;
    }

    @Override
    public Page<Comments> getCommentsByPostId(UUID postId, Pageable pageable) {
        return null;
    }

    @Override
    public void deleteComment(UUID commentId) {

    }

    @Override
    public String updateComments() {
        return null;
    }
}
