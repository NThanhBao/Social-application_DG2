package com.Social.application.DG2.controller;

import com.Social.application.DG2.service.AvatarService;
import com.Social.application.DG2.util.annotation.CheckLogin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/avatar")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @CheckLogin
    @PostMapping("/upload/{objectName}")
    public ResponseEntity<String> uploadAvt(@RequestParam("filePath") String filePath) {
        try {
            avatarService.uploadAvatar(filePath);
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file!");
        }
    }
    @CheckLogin
    @DeleteMapping("/delete/{objectName}")
    public ResponseEntity<String> deleteAvt(@RequestParam("objectName") String objectName) {
        try {
            avatarService.deleteAvatar(objectName);
            return ResponseEntity.ok("File deleted successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete file!");
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}