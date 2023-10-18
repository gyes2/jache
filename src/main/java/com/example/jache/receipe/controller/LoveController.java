package com.example.jache.receipe.controller;

import com.example.jache.constant.dto.ApiResponse;
import com.example.jache.constant.enums.CustomResponseStatus;
import com.example.jache.receipe.dto.ReceipeDto;
import com.example.jache.receipe.dto.ReceipeLoveDto;
import com.example.jache.receipe.service.LoveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class LoveController {
    private final LoveService loveService;
    @PostMapping("/love")
    public ResponseEntity<ApiResponse<String>> love(@RequestBody ReceipeLoveDto.LoveReqDto love, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        loveService.love(love.getReceipeId(),userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS));
    }

    @DeleteMapping("/unlove/{receipeId}")
    public ResponseEntity<ApiResponse<String>> unlove(@PathVariable Long receipeId, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        loveService.unLove(receipeId, userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/love/check/status/{receipeId}")
    public ResponseEntity<ApiResponse<ReceipeLoveDto.LoveStatusResDto>> getStatus(@PathVariable Long receipeId, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ReceipeLoveDto.LoveStatusResDto status = loveService.getStatus(receipeId, userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(status,CustomResponseStatus.SUCCESS));
    }

    /**
     * 스크랩한 레시피 조회
     */
    @GetMapping("/love/list")
    public ResponseEntity<ApiResponse<List<ReceipeDto.ReadReceipeResDto>>> getScraps(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<ReceipeDto.ReadReceipeResDto> scraps = loveService.getScrapReceipe(userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(scraps, CustomResponseStatus.SUCCESS));
    }
}
