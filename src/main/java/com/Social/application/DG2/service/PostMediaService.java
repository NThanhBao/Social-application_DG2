package com.Social.application.DG2.service;

import org.springframework.web.multipart.MultipartFile;

public interface PostMediaService {
    void uploadPost(MultipartFile filePath) throws Exception;
    void deletePost(String objectName) throws Exception;

}