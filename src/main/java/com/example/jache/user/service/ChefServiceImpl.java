package com.example.jache.user.service;

import com.example.jache.constant.enums.CustomResponseStatus;
import com.example.jache.constant.exception.CustomException;
import com.example.jache.receipe.dto.ImgUploadDto;
import com.example.jache.s3.service.S3Service;
import com.example.jache.security.jwtTokens.JwtTokenUtil;
import com.example.jache.user.dto.ChefDto;
import com.example.jache.user.entity.Chef;
import com.example.jache.user.entity.enums.Role;
import com.example.jache.user.repository.ChefRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ChefServiceImpl implements ChefService{

    private final ChefRepository chefRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final S3Service s3Service;

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
                .chefImgUrl("https://3rdprojectbucket.s3.ap-northeast-2.amazonaws.com/initial/userInitial.jpg")
                .build();

        chefRepository.save(chef);

        return ChefDto.SignUpResponseDto.builder()
                .chefName(chef.getChefName())
                .build();
    }

    @Override
    public boolean checkDuplicateCheckName(String chefname) {
        if(chefRepository.findByChefName(chefname).isPresent()){
            throw new CustomException(CustomResponseStatus.DUPLICATE_CHEFNAME);
        }
        return true;
    }

    @Override
    public boolean checkDuplicateEmail(String email) {
        if(chefRepository.findChefByEmail(email).isPresent()){
            throw new CustomException(CustomResponseStatus.DUPLICATE_EMAIL);
        }
        return true;
    }

    @Override
    public boolean checkAuthenticateNumber(String authenticateCode) {

        return false;
    }

    @Override
    public ChefDto.SigninResponseDto login(ChefDto.SigninRequestDto signinRequestDto) {
        log.info(signinRequestDto.toString());
        Chef chef = chefRepository.findByChefName(signinRequestDto.getChefName()).orElseThrow(
                () -> new CustomException(CustomResponseStatus.USER_NOT_FOUND)
        );

        if(!passwordEncoder.matches(signinRequestDto.getPassword(), chef.getPassword())){
            throw new CustomException(CustomResponseStatus.BAD_PASSWORD);
        }
        String token = jwtTokenUtil.createToken(chef.getEmail());
        if(chef.getRefreshToken() == null || jwtTokenUtil.isNeedToUpdateRefreshToken(chef.getRefreshToken())){
            String refresh = jwtTokenUtil.createRefreshToken(chef.getEmail());
            chef.modifyRefreshToken(refresh);
            chefRepository.save(chef);
        }
        chefRepository.save(chef);


        return ChefDto.SigninResponseDto.builder()
                .token(token)
                .refresh(chef.getRefreshToken())
                .build();
    }

    @Override
    public void logout() {
        Chef chef = (Chef) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(chef.getRefreshToken() != null){
            chef.modifyRefreshToken(null);
            chefRepository.save(chef);
        }
    }

    @Override
    public ChefDto.GetChefInfoResDto getInfo(String chefName) {
        Chef chef = chefRepository.findByChefName(chefName).orElseThrow(
                ()->new CustomException(CustomResponseStatus.USER_NOT_FOUND)
        );

        return ChefDto.GetChefInfoResDto.builder()
                .chefName(chef.getChefName())
                .chefImgUrl(chef.getChefImgUrl())
                .chefDetial(chef.getChefDetail())
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
            chefRepository.save(chef);

            return ChefDto.RefreshResDto.builder()
                    .newAccessToken(newAccessToken)
                    .newRefreshToken(newRefreshToken)
                    .build();
        }
    }
    @Override
    public ChefDto.UpdateImgResDto updateMyImage(ImgUploadDto myImgUploadDto, String chefName) {
        Chef chef = chefRepository.findByChefName(chefName).orElseThrow(
                () -> new CustomException(CustomResponseStatus.USER_NOT_FOUND)
        );
        s3Service.deleteFile(chef.getChefImgUrl());
        String updateImgUrl = "";
        if(myImgUploadDto.getMultipartFile().isEmpty()){
            updateImgUrl = "https://3rdprojectbucket.s3.ap-northeast-2.amazonaws.com/initial/userInitial.jpg";
        }
        else{
            updateImgUrl = s3Service.uploadFile(myImgUploadDto.getMultipartFile(), "chef");
        }

        chef.modifyChefImgUrl(updateImgUrl);

        chefRepository.save(chef);

        return ChefDto.UpdateImgResDto.builder()
                .updateImgUrl(chef.getChefImgUrl())
                .build();
    }

    @Override
    public ChefDto.DeleteImgResDto deleteMyImage(ChefDto.DeleteImgReqDto deleteImgReqDto, String chefName) {
        s3Service.deleteFile(deleteImgReqDto.getChefImgUrl());
        Chef chef = chefRepository.findByChefName(chefName).orElseThrow(
                () -> new CustomException(CustomResponseStatus.USER_NOT_FOUND)
        );
        chef.modifyChefImgUrl("https://3rdprojectbucket.s3.ap-northeast-2.amazonaws.com/initial/userInitial.jpg");
        chefRepository.save(chef);

        return ChefDto.DeleteImgResDto.builder()
                .chefImgUrl(chef.getChefImgUrl())
                .build();
    }

    @Override
    public ChefDto.UpdateChefDetailResDto updateMyDetail(ChefDto.UpdateChefDetailReqDto req, String chefName) {
        Chef chef = chefRepository.findByChefName(chefName).orElseThrow(
                () -> new CustomException(CustomResponseStatus.USER_NOT_FOUND)
        );
        chef.modifyChefDetail(req.getChefDetails());
        chefRepository.save(chef);

        return ChefDto.UpdateChefDetailResDto.builder()
                .chefDetails(chef.getChefDetail())
                .build();
    }


    /**
     * 상대방 페이지 관련
     */
    @Override
    public boolean isOtherProfile(String otherName, String chefName) {
        if(otherName.equals(chefName)){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public ChefDto.GetChefInfoResDto getOtherProfile(String otherName) {
        Chef chef = chefRepository.findByChefName(otherName).orElseThrow(
                () -> new CustomException(CustomResponseStatus.USER_NOT_FOUND)
        );
        return new ChefDto.GetChefInfoResDto(chef);
    }


}
