package com.example.jache.user.service;

import com.example.jache.constant.enums.CustomResponseStatus;
import com.example.jache.constant.exception.CustomException;
import com.example.jache.user.dto.ChefDto;
import com.example.jache.user.entity.Chef;
import com.example.jache.user.repository.ChefRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChefServiceImpl implements ChefService{
    private final ChefRepository chefRepository;

    /**
     * 회원가입
     */
    @Override
    public ChefDto.SignUpResponseDto saveChef(ChefDto.SignUpRequestDto signup){

        Chef chef = Chef.builder()
                .chefName(signup.getChefName())
                .password(signup.getPassword())
                .phone(signup.getPhone())
                .email(signup.getEmail())
                .build();
        chefRepository.save(chef);

        return ChefDto.SignUpResponseDto.builder()
                .chefName(chef.getChefName())
                .build();
    }

    @Override
    public boolean checkDuplicateCheckName(String chefname) {
        if(chefRepository.findChefByChefName(chefname).isEmpty()){
            //중복이면 false
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public boolean checkDuplicateEmail(String email) {
        if(chefRepository.findChefByEmail(email).isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean checkAuthenticateNumber(String authenticateCode) {
        return false;
    }

    @Override
    public ChefDto.SigninResponseDto signinChef(ChefDto.SigninRequestDto signin) {
        return null;
    }

    @Override
    public boolean sendEmail(String email) {
        return false;
    }
}
