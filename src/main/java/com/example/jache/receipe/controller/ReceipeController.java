package com.example.jache.receipe.controller;

import com.example.jache.constant.dto.ApiResponse;
import com.example.jache.constant.enums.CustomResponseStatus;
import com.example.jache.receipe.dto.ImgUploadDto;
import com.example.jache.receipe.dto.ReceipeDto;
import com.example.jache.receipe.service.ReceipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReceipeController {
    private final ReceipeService receipeService;

    /**
     * S:3분, A:냉텰, O:원팬, P:파티, T:꿀팁
     */

    /**
     * 레시피 작성 페이지 클릭 시 또는 버튼 클릭 시 요청n
     */
    @PostMapping("/user/receipe/initial")
    public ResponseEntity<ApiResponse<ReceipeDto.InitialReceipeResDto>> receipeInitial(
            Authentication authentication
    ){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ReceipeDto.InitialReceipeResDto initialResult = receipeService.initialReceipe(userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(initialResult, CustomResponseStatus.SUCCESS));
    }

    /**
     * 레시피 등록
     */
    @PutMapping("/user/receipe/create")
    public ResponseEntity<ApiResponse<String>> receipeCreate(
            @RequestPart(value = "receipe") ReceipeDto.CreateReceipeReqDto createReceipe,
            @RequestPart(value = "receipeImg") MultipartFile multipartFile,
            Authentication authentication
    ){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ImgUploadDto receipeImgUploadDto = new ImgUploadDto();
        receipeImgUploadDto.setFile(multipartFile);
        Long receipeId = receipeService.createReceipe(receipeImgUploadDto,createReceipe, userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(receipeId.toString(),CustomResponseStatus.SUCCESS));
    }

    /**
     * 테마별 최신순 조회
     */
    @GetMapping("/receipe/read/{theme}/all/last")
    public ResponseEntity<ApiResponse<List<ReceipeDto.ReadReceipeResDto>>> readReceipesByTheme(@PathVariable String theme){
        List<ReceipeDto.ReadReceipeResDto> receipes = receipeService.readAllReceipesByTheme(theme);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(receipes,CustomResponseStatus.SUCCESS));
    }

    /**
     * 테마별 스크랩순 조회
     */
    @GetMapping("/receipe/read/{theme}/all/scrap")
    public ResponseEntity<ApiResponse<List<ReceipeDto.ReadReceipeResDto>>> readReceipesByThemeAndScrap(@PathVariable String theme){
        List<ReceipeDto.ReadReceipeResDto> receipes = receipeService.readReceipesByThemeOrderByScrap(theme);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(receipes,CustomResponseStatus.SUCCESS));
    }

    /**
     * 레시피 상세 조회
     */
    @GetMapping("/user/receipe/read/detail/{receipeId}")
    public ResponseEntity<ApiResponse<ReceipeDto.ReadReceipeDetailResDto>> readReceipeDetail(@PathVariable Long receipeId){
        ReceipeDto.ReadReceipeDetailResDto receipeDetail = receipeService.readOneReceipe(receipeId);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(receipeDetail,CustomResponseStatus.SUCCESS));
    }

    /**
     * 레시피 삭제
     */
    @DeleteMapping("/user/receipe/delete/{receipeId}")
    public ResponseEntity<ApiResponse<String>> deleteReceipe(@PathVariable Long receipeId, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        receipeService.deleteReceipe(receipeId, userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS));
    }

    /**
     * 레시피 수정
     */
    @PutMapping("/user/receipe/update/{receipeId}")
    public ResponseEntity<ApiResponse<String>> updateReceipe(
            @PathVariable Long receipeId,
            @RequestPart(value = "update") ReceipeDto.CreateReceipeReqDto update,
            @RequestPart(value = "updateImg") MultipartFile multipartFile,
            Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ImgUploadDto updateImgDto = new ImgUploadDto();
        updateImgDto.setFile(multipartFile);
        Long updatereceipeId =  receipeService.updateReceipe(updateImgDto, update, receipeId, userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(updatereceipeId.toString(),CustomResponseStatus.SUCCESS));
    }

    /**
     * 내가 쓴 레시피 조회
     */
    @GetMapping("/user/myreceipe/{theme}")
    public ResponseEntity<ApiResponse<List<ReceipeDto.ReadReceipeResDto>>> readMyReceipe(
            @PathVariable String theme, Authentication authentication
    ){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<ReceipeDto.ReadReceipeResDto> mys = receipeService.readReceipesByThemeAndChef(theme, userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(mys,CustomResponseStatus.SUCCESS));
    }

    /**
     * 상대방이 쓴 레시피 조회
     */
    @GetMapping("/user/other_receipe/{otherName}/{theme}")
    public ResponseEntity<ApiResponse<List<ReceipeDto.ReadReceipeResDto>>> readOtherReceipe(
            @PathVariable(value = "theme") String theme, @PathVariable(value = "otherName") String otherName
    ){
        List<ReceipeDto.ReadReceipeResDto> mys = receipeService.readReceipesByThemeAndChef(theme, otherName);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(mys,CustomResponseStatus.SUCCESS));
    }

    /**
     * 내가 쓴 레시피 여부 확인
     */
    @GetMapping("/user/get/isMyReceipe/{receipeWriter}")
    public ResponseEntity<ApiResponse<Boolean>> getIsMyReceipe(@PathVariable String receipeWriter, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        boolean isMyRecipe = receipeService.getIsMyReceipe(receipeWriter, userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(isMyRecipe,CustomResponseStatus.SUCCESS));
    }
}
