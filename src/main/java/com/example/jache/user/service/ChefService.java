package com.example.jache.user.service;

import com.example.jache.user.dto.ChefDto;

public interface ChefService {
    ChefDto.SignUpResponseDto saveChef(ChefDto.SignUpRequestDto signup);
    boolean checkDuplicateCheckName(String chefname);
    boolean checkDuplicateEmail(String email);
    boolean checkAuthenticateNumber(String authenticateCode);

    ChefDto.SigninResponseDto signinChef(ChefDto.SigninRequestDto signin);

    boolean sendEmail(String email);

}
