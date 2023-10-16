package com.example.jache.receipe.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
@Getter
public class ReceipeImgUploadDto {
    private MultipartFile multipartFile;

    public void setFile(MultipartFile multipartFile){
        if(multipartFile != null){
            this.multipartFile = multipartFile;
        }
    }
}
