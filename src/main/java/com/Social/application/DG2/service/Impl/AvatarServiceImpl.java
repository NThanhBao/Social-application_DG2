package com.Social.application.DG2.service.Impl;

import com.Social.application.DG2.entity.Users;
import com.Social.application.DG2.repositories.UsersRepository;
import com.Social.application.DG2.service.AvatarService;
import io.minio.PutObjectArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.*;
import io.minio.errors.MinioException;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class AvatarServiceImpl implements AvatarService {

    @Autowired
    MinioClient minioClient;
    @Autowired
    UsersRepository usersRepository;



    @Override
    public void uploadAvatar(String filePath) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        Users currentUser = usersRepository.findByUsername(currentUsername);

        String userId = currentUser.getId();

        String bucketName = "avatar";
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

            String avatarUrl = "http://localhost:9000/" + bucketName + "/" + objectName;
            currentUser.setAvatar(avatarUrl);
            usersRepository.save(currentUser);
        } catch (MinioException | IOException e) {
            throw new IOException("Failed to upload file: " + e.getMessage());
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void deleteAvatar(String objectName) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        Users currentUser = usersRepository.findByUsername(currentUsername);

        String userId = currentUser.getId();

        String bucketName = "avatar";
        try {
            String objectFullName = userId + "/" + objectName;

            // Xóa tập tin từ MinIO
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectFullName)
                            .build()
            );

            // Cập nhật cơ sở dữ liệu
            // Đặt đường dẫn avatar của người dùng thành null
            currentUser.setAvatar(null);
            // Lưu thay đổi vào cơ sở dữ liệu
            usersRepository.save(currentUser);
        } catch (MinioException e) {
            throw new IOException("Failed to delete file: " + e.getMessage());
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
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
