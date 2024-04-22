package com.Social.application.DG2.controller;

import com.Social.application.DG2.service.AvatarService;
import com.Social.application.DG2.util.annotation.CheckLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/avatar")
public class AvatarController {

    @Autowired
    private AvatarService minioStorageService;

    @CheckLogin
    @PostMapping(value ="/upload/{filepath}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<String> uploadAvatar(@RequestParam("filePath") MultipartFile filePath) {
        try {
            minioStorageService.uploadAvatar(filePath);
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file: " + e.getMessage());
        }
    }

    @CheckLogin
    @PostMapping(value = "/upload/background/{filepath}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<String> uploadBackGround(@RequestParam("filePath") MultipartFile filePath) {
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
