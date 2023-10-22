package com.example.jache.user.controller;

import com.example.jache.constant.dto.ApiResponse;
import com.example.jache.constant.enums.CustomResponseStatus;
import com.example.jache.constant.exception.CustomException;
import com.example.jache.email.service.EmailService;
import com.example.jache.receipe.dto.ImgUploadDto;
import com.example.jache.security.jwtTokens.JwtTokenUtil;
import com.example.jache.user.dto.ChefDto;
import com.example.jache.user.service.ChefService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class ChefController {
    private final ChefService chefService;
    private final EmailService emailService;
    private final JwtTokenUtil jwtTokenUtil;

    /**
     * 회원가입
     */
    @PostMapping("/all/join")
    public ResponseEntity<ApiResponse<ChefDto.SignUpResponseDto>> join(@RequestBody ChefDto.SignUpRequestDto signup){
        log.info(signup.toString());
        //chefService에서 회원가입 메소드 호출
        ChefDto.SignUpResponseDto registerSignup = chefService.register(signup);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(registerSignup,CustomResponseStatus.SUCCESS));
    }

    /**
     * 아이디 중복 체크
     */
    @GetMapping("/all/check/name/{chefname}")
    public ResponseEntity<ApiResponse<Boolean>> checkdupChefName(@PathVariable String chefname){
        boolean bool = chefService.checkDuplicateCheckName(chefname);
        if(!bool){
            return ResponseEntity.ok().body(ApiResponse.createSuccess(false,CustomResponseStatus.DUPLICATE_CHEFNAME));
        }
        else {
            return ResponseEntity.ok().body(ApiResponse.createSuccess(true,CustomResponseStatus.SUCCESS));
        }
    }

    /**
     * 이메일 중복 체크
     */
    @GetMapping("/all/check/email/{email}")
    public ResponseEntity<ApiResponse<Boolean>> checkDupEmail(@PathVariable String email){
        boolean result = chefService.checkDuplicateEmail(email);
        if(!result){
            return ResponseEntity.ok().body(ApiResponse.createSuccess(false,CustomResponseStatus.DUPLICATE_EMAIL));
        }
        else{
            return ResponseEntity.ok().body(ApiResponse.createSuccess(true,CustomResponseStatus.SUCCESS));
        }
    }

    /**
     * 이메일 보내기
     */
    @GetMapping("/all/email-verification/{email}")
    public ResponseEntity<ApiResponse<String>> sendEmail(@PathVariable String email) throws Exception {
        log.info(email);
        String code = emailService.sendVerificationCode(email);
        //model.addAttribute("verifacationCode",ApiResponse.createSuccess(code,CustomResponseStatus.SUCCESS));
        log.info("이메일 인증 코드: "+code);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(code,CustomResponseStatus.SUCCESS));
    }

    /**
     * 로그인
     */
    @PostMapping("/all/signin")
    public ResponseEntity<ApiResponse<ChefDto.SigninResponseDto>> signin(@RequestBody ChefDto.SigninRequestDto signin){
        ChefDto.SigninResponseDto signinResponseDto = chefService.login(signin);
        log.info("token: "+ signinResponseDto.getToken());
        log.info("refreshToken: "+ signinResponseDto.getRefresh());

        return ResponseEntity.ok().body(ApiResponse.createSuccess(signinResponseDto,CustomResponseStatus.SUCCESS));
    }

    /**
     * 로그아웃
     */
    @PostMapping("/user/logout")
    public ResponseEntity<ApiResponse<String>> logout(Authentication authentication){
        chefService.logout();
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS));
    }

    /**
     * 리프레시 토큰 재발급
     */
    @PostMapping("/member/refresh")
    public ResponseEntity<ApiResponse<ChefDto.RefreshResDto>> refresh(@RequestHeader("Authorization") String refreshToken){
        String result = jwtTokenUtil.validRefreshToken(refreshToken);
        if(!result.equals("리프레시 토큰이 만료되었습니다.")){
            ChefDto.RefreshResDto refreshResDto = chefService.getRefresh(result);
            return ResponseEntity.ok().body(ApiResponse.createSuccess(refreshResDto,CustomResponseStatus.SUCCESS));
        }
        throw new CustomException(CustomResponseStatus.EXPIRED_TOKEN);
    }

    /**
     * user Info (로그인 후 사이드 바)
     * 마이페이지 상단 유저 정보
     */
    @GetMapping("/user/getUserInfo")
    public ResponseEntity<ApiResponse<ChefDto.GetChefInfoResDto>> getChefInfo(Authentication authentication){
        log.info("authentication: "+authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String chefName = userDetails.getUsername();
        ChefDto.GetChefInfoResDto getChefInfoResDto = chefService.getInfo(chefName);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(getChefInfoResDto,CustomResponseStatus.SUCCESS));
    }

    /**
     * 프로필 사진 수정
     */
    @PutMapping("/user/my/update/img")
    public ResponseEntity<ApiResponse<ChefDto.UpdateImgResDto>> updateMyImg(
            @RequestPart(value = "myImg")MultipartFile multipartFile, Authentication authentication
            ){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ImgUploadDto receipeImgUploadDto = new ImgUploadDto();
        receipeImgUploadDto.setFile(multipartFile);
        ChefDto.UpdateImgResDto update = chefService.updateMyImage(receipeImgUploadDto, userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(update,CustomResponseStatus.SUCCESS));
    }

    /**
     * 프로필 사진 삭제
     */
    @DeleteMapping("/user/my/delete/img")
    public ResponseEntity<ApiResponse<ChefDto.DeleteImgResDto>> deleteMyImg(
            @RequestBody ChefDto.DeleteImgReqDto deleteImgReqDto, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ChefDto.DeleteImgResDto basic = chefService.deleteMyImage(deleteImgReqDto, userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(basic,CustomResponseStatus.SUCCESS));
    }

    /**
     * 내 소개 수정
     */
    @PutMapping("/user/my/update/details")
    public ResponseEntity<ApiResponse<ChefDto.UpdateChefDetailResDto>> updateMyDetails(
            @RequestBody ChefDto.UpdateChefDetailReqDto updateDetail, Authentication authentication
    ){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ChefDto.UpdateChefDetailResDto detail = chefService.updateMyDetail(updateDetail, userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(detail, CustomResponseStatus.SUCCESS));
    }

    //여기서 부터 포스트맨 작성
    /**
     * 상대방 페이지 접속 여부 api
     */
    @GetMapping("/user/get/other_user/{otherchefName}")
    public ResponseEntity<ApiResponse<Boolean>> getOther(
            @PathVariable String otherchefName, Authentication authentication
    ){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        boolean other = chefService.isOtherProfile(otherchefName, userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(other,CustomResponseStatus.SUCCESS));
    }

    /**
     * 상대방 페이지 프로필 얻기
     */
    @GetMapping("/user/get/other_user_info/{otherchefName}")
    public ResponseEntity<ApiResponse<ChefDto.GetChefInfoResDto>> getOtherChefInfo(@PathVariable String otherchefName){
        ChefDto.GetChefInfoResDto otherProfile = chefService.getOtherProfile(otherchefName);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(otherProfile,CustomResponseStatus.SUCCESS));
    }

}
