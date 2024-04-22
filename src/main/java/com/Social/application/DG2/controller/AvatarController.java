package com.Social.application.DG2.controller;

import com.Social.application.DG2.service.AvatarService;
import com.Social.application.DG2.util.annotation.CheckLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/avatar")
public class AvatarController {

    @Autowired
    private AvatarService minioStorageService;

    @CheckLogin
    @PostMapping("/upload/{filepath}")
    public ResponseEntity<String> uploadAvatar(@RequestParam("filePath") String filePath) {
        try {
            minioStorageService.uploadAvatar(filePath);
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file: " + e.getMessage());
        }
    }

    @CheckLogin
    @PostMapping("/upload/background/{filepath}")
    public ResponseEntity<String> uploadBackGround(@RequestParam("filePath") String filePath) {
        try {
            minioStorageService.uploadBackGround(filePath);
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file: " + e.getMessage());
        }
    }

    @CheckLogin
    @DeleteMapping("/delete/{filepath}")
    public ResponseEntity<String> deleteAvatar(@RequestParam("filePath") String filePath) {
        try {
            minioStorageService.deleteAvatar(filePath);
            return ResponseEntity.ok("File deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting file: " + e.getMessage());
        }
    }

    @CheckLogin
    @DeleteMapping("/delete/background/{filepath}")
    public ResponseEntity<String> deleteBacGround(@RequestParam("filePath") String filePath) {
        try {
            minioStorageService.deleteBackGround(filePath);
            return ResponseEntity.ok("File deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting file: " + e.getMessage());
        }
    }
}
