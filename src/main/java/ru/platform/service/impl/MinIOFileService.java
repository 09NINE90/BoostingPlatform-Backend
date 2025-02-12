package ru.platform.service.impl;

import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.platform.config.MinIOConfig;
import ru.platform.service.IMinIOFileService;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinIOFileService implements IMinIOFileService {

    private final MinIOConfig minIOConfig;
    @Override
    public void createBucketWithPolicy(String bucketName) throws Exception {
        boolean bucketExists = minIOConfig.minioClient().bucketExists(BucketExistsArgs.builder()
                .bucket(bucketName)
                .build());

        if (bucketExists) {
            return;
        }

        MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder()
                .bucket(bucketName).build();
        minIOConfig.minioClient().makeBucket(makeBucketArgs);

        String policyJson = "{\n" +
                "  \"Version\": \"2012-10-17\",\n" +
                "  \"Statement\": [\n" +
                "    {\n" +
                "      \"Effect\": \"Allow\",\n" +
                "      \"Principal\": \"*\",\n" +
                "      \"Action\": [\"s3:GetObject\"],\n" +
                "      \"Resource\": [\"arn:aws:s3:::" + bucketName + "/*\"]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        minIOConfig.minioClient().setBucketPolicy(SetBucketPolicyArgs.builder()
                .bucket(bucketName)
                .config(policyJson)
                .build());
    }

    @Override
    public String uploadBaseOrderImage(MultipartFile imageFile) {
        try {
            String bucketName = "orders-images";
            return uploadImage(imageFile, bucketName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    @Override
    public String uploadGameImage(MultipartFile imageFile) {
        try {
            String bucketName = "games-images";
            return uploadImage(imageFile, bucketName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    private String uploadImage(MultipartFile imageFile, String bucketName) throws Exception {
        String fileName = UUID.randomUUID() + "-" + imageFile.getOriginalFilename();
        createBucketWithPolicy(bucketName);
        minIOConfig.minioClient().putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .stream(imageFile.getInputStream(), imageFile.getSize(), -1)
                        .contentType(imageFile.getContentType())
                        .build()
        );
        return getPresignedUrl(bucketName, fileName);
    }

    public String getPresignedUrl(String bucketName, String objectName) {
        try {
            return minIOConfig.minioClient().getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(objectName)
                            .build());
        } catch (Exception e) {
            log.error("Ошибка получения URL тайла: {}", e.getMessage());
            return null;
        }
    }
}
