package com.Social.application.DG2.service;

import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
    void createMedia(MultipartFile multipartFile) throws Exception;

    String uploadMedia(MultipartFile filePath) throws Exception;
    void deletePost(String objectName) throws Exception;

}