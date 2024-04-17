package com.Social.application.DG2.controller;

import com.Social.application.DG2.dto.PostsDto;
import com.Social.application.DG2.entity.Posts;
import com.Social.application.DG2.service.PostMediaService;
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
    private PostMediaService postService;

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

    @GetMapping("/getAll")
    public ResponseEntity<List<Posts>> getAllPosts() {
        List<Posts> posts = postsService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/getAll/count")
    public ResponseEntity<Integer> getNumberOfPosts() {
        int numberOfPosts = postsService.getNumberOfPosts();
        return new ResponseEntity<>(numberOfPosts, HttpStatus.OK);
    }

    @GetMapping("/get/count/user/{userId}")
    public int getNumberOfPostsByUserId() {
        return postsService.getNumberOfPostsByUserId();
    }

    @PutMapping("/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable("postId") UUID postId, @RequestBody PostsDto updatedPost) {
        try {
            postsService.updatePost(postId, updatedPost);
            return new ResponseEntity<>("Post updated successfully", HttpStatus.OK);
        } catch (EntityNotFoundException | AccessDeniedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while updating the post", HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

    @CheckLogin
    @PostMapping("/upload/{filepath}")
    public ResponseEntity<String> uploadPostVideo(@RequestParam("filePath") String filePath) {
        try {
            postService.uploadPost(filePath);
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file: " + e.getMessage());
        }
    }

    @CheckLogin
    @DeleteMapping("/delete/{filepath}")
    public ResponseEntity<String> deletePostVideos(@RequestParam("filePath") String filePath) {
        try {
            postService.deletePost(filePath);
            return ResponseEntity.ok("File deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting file: " + e.getMessage());
        }
    }
}
