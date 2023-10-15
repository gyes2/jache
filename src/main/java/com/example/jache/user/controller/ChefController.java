package com.example.jache.user.controller;

import com.example.jache.constant.dto.ApiResponse;
import com.example.jache.constant.enums.CustomResponseStatus;
import com.example.jache.constant.exception.CustomException;
import com.example.jache.email.service.EmailService;
import com.example.jache.security.jwtTokens.JwtTokenUtil;
import com.example.jache.user.dto.ChefDto;
import com.example.jache.user.entity.Chef;
import com.example.jache.user.service.ChefService;
import com.example.jache.user.service.ChefServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
     * 이메일 중복 체크
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
     * 리프레시 토큰 재발급
     */
    @PostMapping("/member/refresh")
    public ResponseEntity<ApiResponse<ChefDto.RefreshResDto>> refresh(@RequestHeader("Authorization") String refreshToken){
        String result = jwtTokenUtil.validRefreshToken(refreshToken);
        if(!result.equals("만료된 토큰입니다.")){
            ChefDto.RefreshResDto refreshResDto = chefService.getRefresh(result);
            return ResponseEntity.ok().body(ApiResponse.createSuccess(refreshResDto,CustomResponseStatus.SUCCESS));
        }
        throw new CustomException(CustomResponseStatus.EXPIRED_TOKEN);

    }

    /**
     * user Info (로그인 후 사이드 바)
     */
    @GetMapping("/user/getUserInfo")
    public ResponseEntity<ApiResponse<ChefDto.GetChefInfoResDto>> getChefInfo(Authentication authentication){
        log.info("authentication: "+authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String chefName = userDetails.getUsername();
        ChefDto.GetChefInfoResDto getChefInfoResDto = chefService.getInfo(chefName);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(getChefInfoResDto,CustomResponseStatus.SUCCESS));
    }



}
