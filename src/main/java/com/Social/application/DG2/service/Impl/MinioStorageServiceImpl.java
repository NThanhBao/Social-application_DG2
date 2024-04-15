package com.Social.application.DG2.service.Impl;


import com.Social.application.DG2.config.MinIOConfig;
import com.Social.application.DG2.service.MinioStorageService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class MinioStorageServiceImpl implements MinioStorageService {

    @Value("${minio.endpoint}")
    private String minioEndpoint;
    @Value("${minio.bucketName}")
    private String bucketName;
    @Autowired
    private MinIOConfig minIOConfig;
    @Override
    public String uploadImage(MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        try {
            boolean found = minIOConfig.minioClient().bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minIOConfig.minioClient().makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            minIOConfig.minioClient().uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .filename(file.getOriginalFilename())
                            .contentType(file.getContentType())
                            .build()
            );
            return "Upload successful! File name: " + fileName;
        } catch (MinioException | IOException e) {
            e.printStackTrace();
            return "Upload failed: " + e.getMessage();
        }
    }
}