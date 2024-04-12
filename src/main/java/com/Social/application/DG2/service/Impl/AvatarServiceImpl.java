package com.Social.application.DG2.service.Impl;

import com.Social.application.DG2.entity.Users;
import com.Social.application.DG2.repositories.UsersRepository;
import com.Social.application.DG2.service.AvatarService;
import io.minio.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.io.*;
import java.io.InputStream;
import java.io.File;


@Service
public class AvatarServiceImpl implements AvatarService {
    @Autowired
    private MinioClient minioClient;
    @Autowired
    private UsersRepository usersRepository;
    String bucketName = "avatar";
    String bucketNameBackground = "background";
    @Override
    public void uploadAvatar( String filePath) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        Users currentUser = usersRepository.findByUsername(currentUsername);
        String userId = currentUser.getId();

        try (InputStream inputStream = new FileInputStream(filePath)) {
            String objectName = userId + "/" + new File(filePath).getName();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType(getContentType(objectName))
                            .build()
            );
        }catch (Exception e) {
            throw new Exception("Error uploading file to MinIO", e);
        }
    }

    @Override
    public void uploadBackGround( String filePath) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        Users currentUser = usersRepository.findByUsername(currentUsername);
        String userId = currentUser.getId();

        try (InputStream inputStream = new FileInputStream(filePath)) {
            String objectName = userId + "/" + new File(filePath).getName();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketNameBackground)
                            .object(objectName)
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType(getContentType(objectName))
                            .build()
            );
        }catch (Exception e) {
            throw new Exception("Error uploading file to MinIO", e);
        }
    }

    @Override
    public void deleteAvatar(String objectName) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        Users currentUser = usersRepository.findByUsername(currentUsername);
        String userId = currentUser.getId();

        try {
            String objectFullName =userId + "/" + objectName;
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectFullName)
                    .build());
        } catch (Exception e) {
            throw new Exception("Error deleting file from MinIO", e);
        }
    }

    @Override
    public void deleteBackGround(String objectName) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        Users currentUser = usersRepository.findByUsername(currentUsername);
        String userId = currentUser.getId();

        try {
            String objectFullName =userId + "/" + objectName;
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketNameBackground)
                    .object(objectFullName)
                    .build());
        } catch (Exception e) {
            throw new Exception("Error deleting file from MinIO", e);
        }
    }

    private String getContentType(String fileName) {
        String fileExtension = getFileExtension(fileName).toLowerCase();
        return switch (fileExtension) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            case "bmp" -> "image/bmp";
            default -> "application/octet-stream"; // Kiểu MIME mặc định
        };
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        return (lastDotIndex == -1) ? "" : fileName.substring(lastDotIndex + 1);
    }
}