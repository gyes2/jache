package com.example.jache.user.service;

import com.example.jache.user.dto.ChefDto;
import com.example.jache.user.entity.Chef;

public interface ChefService {

    ChefDto.SignUpResponseDto register(ChefDto.SignUpRequestDto signup);

    boolean checkDuplicateCheckName(String chefname);
    boolean checkDuplicateEmail(String email);
    boolean checkAuthenticateNumber(String authenticateCode);

    ChefDto.SigninResponseDto login(ChefDto.SigninRequestDto signinRequestDto);

    ChefDto.GetChefInfoResDto getInfo(String chefName);

    ChefDto.RefreshResDto getRefresh(String refresh);
}
