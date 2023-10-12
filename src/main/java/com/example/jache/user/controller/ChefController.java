package com.example.jache.user.controller;

import com.example.jache.constant.dto.ApiResponse;
import com.example.jache.constant.enums.CustomResponseStatus;
import com.example.jache.email.service.EmailService;
import com.example.jache.user.dto.ChefDto;
import com.example.jache.user.service.ChefService;
import com.example.jache.user.service.ChefServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/join")
    public String join(Model model, @RequestBody ChefDto.SignUpRequestDto signup){
        log.info(signup.toString());
        //chefService에서 회원가입 메소드 호출
        ChefDto.SignUpResponseDto registerSignup = chefService.saveChef(signup);
        model.addAttribute("signupResponseDto", ApiResponse.createSuccess(registerSignup, CustomResponseStatus.SUCCESS));
        return "main";
    }

    /**
     * 아이디 중복 체크
     */
    @GetMapping("/check/name/{chefname}")
    public ResponseEntity<ApiResponse<Boolean>> checkdupChefName(@RequestParam String chefname){
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
    @GetMapping("/check/email/{email}")
    public ResponseEntity<ApiResponse<Boolean>> checkDupEmail(@RequestParam(value = "email")String email){
        boolean result = chefService.checkDuplicateEmail(email);
        if(!result){
            return ResponseEntity.ok().body(ApiResponse.createSuccess(false,CustomResponseStatus.DUPLICATE_EMAIL));
        }
        else{
            return ResponseEntity.ok().body(ApiResponse.createSuccess(true,CustomResponseStatus.SUCCESS));
        }
    }


    @GetMapping("/signin")
    public String signin(Model model, @RequestBody ChefDto.SigninRequestDto signin){

        return null;
    }

    @PostMapping("/email-verification")
    public ResponseEntity<ApiResponse<String>> sendEmail(@RequestBody ChefDto.SendEmailRequestDto send) throws Exception {
        log.info(send.getEmail());
        String code = emailService.sendVerificationCode(send.getEmail());
        //model.addAttribute("verifacationCode",ApiResponse.createSuccess(code,CustomResponseStatus.SUCCESS));
        log.info("이메일 인증 코드: "+code);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(code,CustomResponseStatus.SUCCESS));
    }





}
