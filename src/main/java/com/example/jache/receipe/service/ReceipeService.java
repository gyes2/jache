package com.example.jache.receipe.service;

import com.example.jache.receipe.dto.ImgUploadDto;
import com.example.jache.receipe.dto.ReceipeDto;

import java.util.List;

public interface ReceipeService {

    ReceipeDto.InitialReceipeResDto initialReceipe(ReceipeDto.InitialReceipeReqDto initial,String chefName);
    Long createReceipe(ImgUploadDto uploadDto, ReceipeDto.CreateReceipeReqDto createReceipeReqDto, String chefName);
    ReceipeDto.ReadReceipeDetailResDto readOneReceipe(Long receipeId);

    List<ReceipeDto.ReadReceipeResDto> readReceipesByTheme(String theme);

    List<ReceipeDto.ReadReceipeResDto> readReceipesByThemeOrderByScrap(String theme);

    ReceipeDto.ReadReceipeDetailResDto updateReceipe(ReceipeDto.CreateReceipeReqDto createReceipeReqDto);

    void deleteReceipe(Long receipeId,String chefName);

    List<ReceipeDto.ReadReceipeResDto> readMyReceipesByTheme(String theme, String chefName);

}
