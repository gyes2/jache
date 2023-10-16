package com.example.jache.receipe.controller;

import com.example.jache.constant.dto.ApiResponse;
import com.example.jache.constant.enums.CustomResponseStatus;
import com.example.jache.receipe.dto.ReceipeDto;
import com.example.jache.receipe.dto.ReceipeImgUploadDto;
import com.example.jache.receipe.service.ReceipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class ReceipeController {
    private final ReceipeService receipeService;

    /**
     * S:3분, A:냉텰, O:원팬, P:파티, T:꿀팁
     */
    @PostMapping("/receipe/initial")
    public ResponseEntity<ApiResponse<ReceipeDto.InitialReceipeResDto>> receipeInitial(
            @RequestBody ReceipeDto.InitialReceipeReqDto initial, Authentication authentication
    ){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ReceipeDto.InitialReceipeResDto initialResult = receipeService.initialReceipe(initial, userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(initialResult, CustomResponseStatus.SUCCESS));
    }

    @PostMapping("/receipe/create")
    public ResponseEntity<ApiResponse<String>> receipeCreate(
            @RequestPart(value = "receipe") ReceipeDto.CreateReceipeReqDto createReceipe,
            @RequestPart(value = "receipeImg") MultipartFile multipartFile,
            Authentication authentication
    ){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ReceipeImgUploadDto receipeImgUploadDto = new ReceipeImgUploadDto();
        receipeImgUploadDto.setFile(multipartFile);
        Long receipeId = receipeService.createReceipe(receipeImgUploadDto,createReceipe, userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(receipeId.toString(),CustomResponseStatus.SUCCESS));
    }
}
