package com.example.jache.user.controller;

import com.example.jache.constant.dto.ApiResponse;
import com.example.jache.constant.enums.CustomResponseStatus;
import com.example.jache.user.dto.ChefDto;
import com.example.jache.user.service.ChefService;
import com.example.jache.user.service.ChefServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class ChefController {
    private final ChefService chefService;
    @PostMapping("/join")
    public String join(Model model, @RequestBody ChefDto.SignUpRequestDto signup){
        log.info(signup.toString());
        //chefService에서 회원가입 메소드 호출
        ChefDto.SignUpResponseDto registerSignup = chefService.saveChef(signup);
        model.addAttribute("signupResponseDto", ApiResponse.createSuccess(registerSignup, CustomResponseStatus.SUCCESS));
        return "main";
    }

    @GetMapping("/check/name/{chefname}")
    public String checkdupChefName(Model model, @RequestParam String chefname){
        boolean bool = chefService.checkDuplicateCheckName(chefname);
        model.addAttribute("checkChefName",ApiResponse.createSuccess(bool,CustomResponseStatus.SUCCESS));
        return "/api/join";
    }
    @GetMapping("/signin")
    public String signin(Model model, @RequestBody ChefDto.SigninRequestDto signin){

        return null;
    }


}
