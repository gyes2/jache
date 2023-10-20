package com.example.jache.s3.service;


import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.jache.constant.enums.CustomResponseStatus;
import com.example.jache.constant.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    public String uploadFile(MultipartFile multipartFile, String purpose) {

        String uploadFilename = createFileName(multipartFile.getOriginalFilename());

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());
        try{
            amazonS3.putObject(bucket+"/"+purpose, uploadFilename, multipartFile.getInputStream(), metadata);
        }catch (IOException e){
            throw new CustomException(CustomResponseStatus.IMAGE_UPLOAD_ERROR);
        }


        return amazonS3.getUrl(bucket, purpose+"/"+uploadFilename).toString();
    }

    // 이미지파일명 중복 방지
    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    // 파일 유효성 검사
    private String getFileExtension(String fileName) {
        if (fileName.isEmpty()) {
            throw new CustomException(CustomResponseStatus.WRONG_INPUT_IMAGE);
        }
        ArrayList<String> fileValidate = new ArrayList<>();
        fileValidate.add(".jpg");
        fileValidate.add(".jpeg");
        fileValidate.add(".png");
        fileValidate.add(".JPG");
        fileValidate.add(".JPEG");
        fileValidate.add(".PNG");
        String idxFileName = fileName.substring(fileName.lastIndexOf("."));
        if (!fileValidate.contains(idxFileName)) {
            throw new CustomException(CustomResponseStatus.WRONG_INPUT_IMAGE);
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public void deleteFile(String fileUrl) {
        try{
            String key = fileUrl.substring(56);
            amazonS3.deleteObject(bucket,key);
        }catch (SdkClientException e){
            throw new CustomException(CustomResponseStatus.S3_IMG_DELETE_ERROR);
        }
    }
}
