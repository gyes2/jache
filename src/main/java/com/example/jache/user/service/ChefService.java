package com.example.jache.user.service;

import com.example.jache.receipe.dto.ImgUploadDto;
import com.example.jache.user.dto.ChefDto;

public interface ChefService {

    ChefDto.SignUpResponseDto register(ChefDto.SignUpRequestDto signup);

    boolean checkDuplicateCheckName(String chefname);
    boolean checkDuplicateEmail(String email);
    boolean checkAuthenticateNumber(String authenticateCode);

    ChefDto.SigninResponseDto login(ChefDto.SigninRequestDto signinRequestDto);

    void logout();

    ChefDto.GetChefInfoResDto getInfo(String chefName);

    ChefDto.RefreshResDto getRefresh(String refresh);

    ChefDto.UpdateImgResDto updateMyImage(ImgUploadDto receipeImgUploadDto, String chefName);

    ChefDto.DeleteImgResDto deleteMyImage(ChefDto.DeleteImgReqDto deleteImgReqDto, String chefName);

    ChefDto.UpdateChefDetailResDto updateMyDetail(ChefDto.UpdateChefDetailReqDto req, String chefName);

    //상대방 페이지
    boolean isOtherProfile(String otherName, String chefName);

    ChefDto.GetChefInfoResDto getOtherProfile(String otherName);
}
