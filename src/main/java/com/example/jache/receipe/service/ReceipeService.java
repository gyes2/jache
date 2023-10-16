package com.example.jache.receipe.service;

import com.example.jache.receipe.dto.ReceipeDto;
import com.example.jache.receipe.dto.ReceipeImgUploadDto;
import com.example.jache.receipe.entity.Receipe;

import java.util.List;

public interface ReceipeService {

    ReceipeDto.InitialReceipeResDto initialReceipe(ReceipeDto.InitialReceipeReqDto initial,String chefName);
    Long createReceipe(ReceipeImgUploadDto uploadDto,ReceipeDto.CreateReceipeReqDto createReceipeReqDto, String chefName);
    ReceipeDto.ReadReceipeDetailResDto readOneReceipe(Long receipeId);

    List<ReceipeDto.ReadReceipeResDto> readReceipesTheme(String theme);

    ReceipeDto.ReadReceipeDetailResDto updateReceipe(ReceipeDto.CreateReceipeReqDto createReceipeReqDto);

    void deleteReceipe(Long receipeId);
}
