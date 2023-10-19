package com.example.jache.receipe.service;

import com.example.jache.receipe.dto.ImgUploadDto;
import com.example.jache.receipe.dto.ReceipeDto;

import java.util.List;

public interface ReceipeService {

    ReceipeDto.InitialReceipeResDto initialReceipe(String chefName);
    Long createReceipe(ImgUploadDto uploadDto, ReceipeDto.CreateReceipeReqDto createReceipeReqDto, String chefName);
    ReceipeDto.ReadReceipeDetailResDto readOneReceipe(Long receipeId);

    List<ReceipeDto.ReadReceipeResDto> readAllReceipesByTheme(String theme);

    List<ReceipeDto.ReadReceipeResDto> readReceipesByThemeOrderByScrap(String theme);

    Long updateReceipe(ImgUploadDto updateImgDto, ReceipeDto.CreateReceipeReqDto createReceipeReqDto, long receipeId, String chefName);

    void deleteReceipe(Long receipeId,String chefName);

    List<ReceipeDto.ReadReceipeResDto> readReceipesByThemeAndChef(String theme, String chefName);


    boolean getIsMyReceipe(String receipeWriter, String chefName);

}
