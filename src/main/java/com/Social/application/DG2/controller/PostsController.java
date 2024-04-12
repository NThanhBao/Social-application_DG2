package com.Social.application.DG2.controller;

import com.Social.application.DG2.config.MinIOConfig;
import com.Social.application.DG2.dto.PostsDto;
import com.Social.application.DG2.entity.Posts;
import com.Social.application.DG2.entity.Users;
import com.Social.application.DG2.service.PostMediaService;
import com.Social.application.DG2.service.PostsService;
import com.Social.application.DG2.util.annotation.CheckLogin;
import com.Social.application.DG2.util.exception.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
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
    @CheckLogin
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
    @CheckLogin
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Posts>> getPostsByUserId(@PathVariable("userId") UUID userId) {
        List<Posts> posts = postsService.getPostsByUserId(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Posts>> getAllPosts() {
        List<Posts> posts = postsService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/all/count")
    public ResponseEntity<Integer> getNumberOfPosts() {
        int numberOfPosts = postsService.getNumberOfPosts();
        return new ResponseEntity<>(numberOfPosts, HttpStatus.OK);
    }

    @GetMapping("/user/count/{userId}")
    public ResponseEntity<Integer> getNumberOfPostsByUserId(@PathVariable("userId") UUID userId) {
        int numberOfPosts = postsService.getNumberOfPostsByUserId(userId);
        return ResponseEntity.ok(numberOfPosts);
    }

    @CheckLogin
    @PostMapping(value = "/upload/{filePath}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<String> uploadPostVideo(@RequestParam("filePath") MultipartFile filePath) {
        try {
            postService.uploadPost(filePath);
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file: " + e.getMessage());
        }
    }

    @CheckLogin
    @DeleteMapping("/delete/{filePath}")
    public ResponseEntity<String> deletePostVideos(@RequestParam("filePath") String filePath) {
        try {
            postService.deletePost(filePath);
            return ResponseEntity.ok("File deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting file: " + e.getMessage());
        }
    }

    @CheckLogin
    @GetMapping("/count")
    public ResponseEntity<Integer> getNumberOfPostsByLoggedInUser() {
        try {
            int numberOfPosts = postsService.getNumberOfPostsByLoggedInUser();
            return ResponseEntity.ok(numberOfPosts);
        } catch (EntityNotFoundException ex) {
            throw new NotFoundException("User not found"+ ex);
        }
    }

    @CheckLogin
    @GetMapping("/user")
    public ResponseEntity<List<Posts>> getListOfPostsByLoggedInUser() {
        try {
            List<Posts> posts = postsService.getListOfPostsByLoggedInUser();
            return ResponseEntity.ok(posts);
        } catch (EntityNotFoundException ex) {
            throw new NotFoundException("User not found"+ ex);
        }
    }
}
