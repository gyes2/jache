package com.example.jache.user.service;

import com.example.jache.constant.enums.CustomResponseStatus;
import com.example.jache.constant.exception.CustomException;
import com.example.jache.security.jwtTokens.JwtTokenUtil;
import com.example.jache.user.dto.ChefDto;
import com.example.jache.user.entity.Chef;
import com.example.jache.user.entity.enums.Role;
import com.example.jache.user.repository.ChefRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ChefServiceImpl implements ChefService{

    private final ChefRepository chefRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;


    /**
     * 회원가입
     */
    @Override
    public ChefDto.SignUpResponseDto register(ChefDto.SignUpRequestDto signup){

        Chef chef = Chef.builder()
                .chefName(signup.getChefName())
                .password(passwordEncoder.encode(signup.getPassword()))
                .phone(signup.getPhone())
                .email(signup.getEmail())
                .role(Role.ROLE_USER)
                .build();
        chefRepository.save(chef);

        return ChefDto.SignUpResponseDto.builder()
                .chefName(chef.getChefName())
                .build();
    }

    @Override
    public boolean checkDuplicateCheckName(String chefname) {
        if(chefRepository.findByChefName(chefname) == null){
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
    public ChefDto.SigninResponseDto login(ChefDto.SigninRequestDto signinRequestDto) {
        log.info(signinRequestDto.toString());
        Chef chef = chefRepository.findByChefName(signinRequestDto.getChefName()).orElseThrow();
        if(chef == null){
            throw new CustomException(CustomResponseStatus.USER_NOT_FOUND);
        }
        if(!passwordEncoder.matches(signinRequestDto.getPassword(), chef.getPassword())){
            throw new CustomException(CustomResponseStatus.BAD_PASSWORD);
        }
        String token = jwtTokenUtil.createToken(chef.getEmail());
        if(chef.getRefreshToken() == null || jwtTokenUtil.isNeedToUpdateRefreshToken(chef.getRefreshToken())){
            String refresh = jwtTokenUtil.createRefreshToken(chef.getEmail());
            chef.modifyRefreshToken(refresh);
        }

        return ChefDto.SigninResponseDto.builder()
                .token(token)
                .refresh(chef.getRefreshToken())
                .build();
    }

    @Override
    public ChefDto.GetChefInfoResDto getInfo(String chefName) {
        Chef chef = chefRepository.findByChefName(chefName).orElseThrow(
                ()->new CustomException(CustomResponseStatus.USER_NOT_FOUND)
        );

        return ChefDto.GetChefInfoResDto.builder()
                .chefName(chef.getChefName())
                .chefImgUrl(chef.getChefImgUrl())
                .build();
    }

    @Override
    public ChefDto.RefreshResDto getRefresh(String refresh) {
        String resolvedToken = jwtTokenUtil.resolveToken(refresh);
        String email = jwtTokenUtil.getEmail(resolvedToken);
        String savedRefreshToken = jwtTokenUtil.createRefreshToken(email);

        if(refresh.isEmpty() || !refresh.equals(savedRefreshToken)){
            throw new CustomException(CustomResponseStatus.INVALID_REFRESH_TOKEN);
        }
        else{
            String newAccessToken = jwtTokenUtil.createToken(email);
            String newRefreshToken = jwtTokenUtil.createRefreshToken(email);
            Chef chef = chefRepository.findChefByEmail(email).orElseThrow(
                    ()-> new CustomException(CustomResponseStatus.USER_NOT_FOUND)
            );
            chef.modifyRefreshToken(newRefreshToken);
            return ChefDto.RefreshResDto.builder()
                    .newAccessToken(newAccessToken)
                    .newRefreshToken(newRefreshToken)
                    .build();
        }
    }


}
