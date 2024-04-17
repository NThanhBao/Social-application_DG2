package com.Social.application.DG2.service;

public interface AvatarService {
    void uploadAvatar(String filePath) throws Exception;
    void uploadBackGround(String filePath) throws Exception;
    void deleteAvatar(String objectName) throws Exception;
    void deleteBackGround(String objectName) throws Exception;
}
