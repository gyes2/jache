package com.example.jache.receipe.service;

import com.example.jache.receipe.dto.ReceipeDto;
import com.example.jache.receipe.dto.ReceipeLoveDto;
import com.example.jache.receipe.entity.Love;

import java.util.List;

public interface LoveService {


    void love(Long receipeId, String chefName);

    void unLove(Long receipeId, String chefName);

    ReceipeLoveDto.LoveStatusResDto getStatus(Long receipeId, String chefName);

    List<ReceipeDto.ReadReceipeResDto> getScrapReceipe(String chefName);
}
