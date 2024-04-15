package com.Social.application.DG2.controller;

import com.Social.application.DG2.service.MinioStorageService;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/images")
public class ImageController {
    @Autowired
    private MinioStorageService minioStorageService;

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return minioStorageService.uploadImage(file);
    }
}
