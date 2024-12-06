package ru.platform.service;

import org.springframework.web.multipart.MultipartFile;

public interface IMinIOFileService {

    void createBucketWithPolicy(String bucketName) throws Exception;

    String uploadBaseOrderImage(MultipartFile imageFile);

    String uploadGameImage(MultipartFile imageFile);
}
