package com.Social.application.DG2.service.Impl;

import com.Social.application.DG2.entity.Users;
import com.Social.application.DG2.repositories.UsersRepository;
import com.Social.application.DG2.service.PostMediaService;
import com.Social.application.DG2.util.exception.NotFoundException;
import io.minio.*;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class PostMediaServiceImpl implements PostMediaService {
    @Autowired
    private MinioClient minioClient;
    @Autowired
    private UsersRepository usersRepository;
    String bucketName = "posts";

    @Override
    public void uploadPost(String filePath) throws Exception {
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
    public void deletePost(String objectName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        Users currentUser = usersRepository.findByUsername(currentUsername);
        String userId = currentUser.getId();

        try {
            String filepath =userId + "/" + objectName;

            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filepath)
                    .build()
            );
            throw new NotFoundException("Lỗi khi xóa tệp đính kèm từ MinIO: ");
        } catch (MinioException e) {
            throw new NotFoundException("Lỗi khi xóa tệp đính kèm từ MinIO: ");
        } catch (Exception e) {
            throw new RuntimeException("Lỗi không xác định khi xóa bài post và tệp đính kèm", e);
        }
    }

    private String getContentType(String fileName) {
        String fileExtension = getFileExtension(fileName).toLowerCase();
        return switch (fileExtension) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            case "bmp" -> "image/bmp";
            case "mp4" -> "video/mp4";
            case "avi" -> "video/x-msvideo";
            case "mov" -> "video/quicktime";
            case "wmv" -> "video/x-ms-wmv";
            default -> "application/octet-stream"; // Kiểu MIME mặc định
        };
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        return (lastDotIndex == -1) ? "" : fileName.substring(lastDotIndex + 1);
    }
}
