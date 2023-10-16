package com.example.jache.receipe.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class OrderImgUploadDto {

    private MultipartFile orderImg;

    public void setFile(MultipartFile multipartFile){
        if(multipartFile != null){
            this.orderImg = multipartFile;
        }
    }
}
