package com.Social.application.DG2.controller;

import com.Social.application.DG2.dto.PostsDto;
import com.Social.application.DG2.entity.Posts;
import com.Social.application.DG2.service.PostsService;
import com.Social.application.DG2.util.annotation.CheckLogin;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostsController {
    private final PostsService postsService;

    @Autowired
    public PostsController(PostsService postsService) {
        this.postsService = postsService;
    }

    @CheckLogin
    @PostMapping("/create")
    public ResponseEntity<Posts> createPosts(@RequestBody PostsDto postDto) {
        Posts newPost = postsService.createPosts(postDto);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @GetMapping("/get/user/{userId}")
    public ResponseEntity<List<Posts>> getPostsByUserId(@PathVariable("userId") UUID userId) {
        List<Posts> posts = postsService.getPostsByUserId(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Posts>> getAllPosts() {
        List<Posts> posts = postsService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable("postId") UUID postId) {
        try {
            postsService.deletePost(postId);
            return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
        } catch (EntityNotFoundException | AccessDeniedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while deleting the post", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
