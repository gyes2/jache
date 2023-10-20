package com.example.jache.receipe.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ImgUploadDto {

    private MultipartFile multipartFile;

    public void setFile(MultipartFile multipartFile){
        this.multipartFile = multipartFile;

    }
}
