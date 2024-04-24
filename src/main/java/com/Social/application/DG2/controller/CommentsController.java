package com.Social.application.DG2.controller;

import com.Social.application.DG2.entity.Comments;
import com.Social.application.DG2.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @PostMapping
    public Comments addComment(@RequestBody Comments comment) {
        return commentsService.saveComment(comment);
    }

    @PostMapping("/{commentId}")
    public Comments getCommentById(@PathVariable UUID commentId) {
        return commentsService.postCommentById(commentId);
    }

    @GetMapping("/post/{postId}")
    public Page<Comments> getCommentsByPostId(@PathVariable UUID postId, Pageable pageable) {
        return commentsService.getCommentsByPostId(postId, pageable);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable UUID commentId) {
        commentsService.deleteComment(commentId);
    }
}
