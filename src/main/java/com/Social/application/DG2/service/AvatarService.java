package com.Social.application.DG2.service;

import org.springframework.web.multipart.MultipartFile;

public interface AvatarService {
    void uploadAvatar(MultipartFile filePath) throws Exception;
    void uploadBackGround(MultipartFile filePath) throws Exception;
    void deleteAvatar(String objectName) throws Exception;
    void deleteBackGround(String objectName) throws Exception;
}
