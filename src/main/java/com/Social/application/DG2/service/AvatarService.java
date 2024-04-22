package com.Social.application.DG2.service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface AvatarService {
    void uploadAvatar(String filePath) throws IOException;
    void deleteAvatar(String objectName) throws IOException, InvalidKeyException, NoSuchAlgorithmException;
}
