package com.example.jache.user.service;

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
        if(chefRepository.findChefByChefName(signup.getChefName()) == null){
            //나중에 custom exception 만들 것
            return null;
        }

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
        return false;
    }

    @Override
    public boolean checkDuplicateEmail(String email) {
        return false;
    }

    @Override
    public boolean checkAuthenticateNumber(String authenticateCode) {
        return false;
    }

    @Override
    public ChefDto.SigninResponseDto signinChef(ChefDto.SigninRequestDto signin) {
        return null;
    }
}
