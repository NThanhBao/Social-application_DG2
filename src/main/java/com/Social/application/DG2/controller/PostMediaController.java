package com.Social.application.DG2.controller;

import com.Social.application.DG2.service.PostMediaService;
import com.Social.application.DG2.util.annotation.CheckLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostMediaController {

    @Autowired
    private PostMediaService postService;

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
