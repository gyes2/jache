package com.example.jache.receipe.controller;

import com.example.jache.constant.dto.ApiResponse;
import com.example.jache.constant.enums.CustomResponseStatus;
import com.example.jache.receipe.dto.IngredientDto;
import com.example.jache.receipe.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;
    /**
     * Ingredient 등록
     */
    @PostMapping("/ingredient/add")
    public ResponseEntity<ApiResponse<IngredientDto.IngredientResDto>> addIngredient(
            @RequestBody IngredientDto.IngredientReqDto ingredientReqDto, Authentication authentication){
        IngredientDto.IngredientResDto ingredientResDto = ingredientService.addIngredient(ingredientReqDto);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(ingredientResDto, CustomResponseStatus.SUCCESS));
    }

    /**
     * 레시피 별 Ingredient 조회
     */

    /**
     * Ingredient 삭제
     */
    @DeleteMapping("/ingredient/delete/{ingredientId}")
    public ResponseEntity<ApiResponse<String>> deleteIngredient(
            @PathVariable Long ingredientId, Authentication authentication
    ){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if(ingredientService.isAuthoirezed(ingredientId, userDetails.getUsername())){
            ingredientService.deleteIngredient(ingredientId);
            return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS));
        }
        else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.createError(CustomResponseStatus.AUTHENTICATED_FAILED));
        }
    }

    /**
     * Ingredient 수정
     */
    @PutMapping("/ingredient/update/{ingredientId}")
    public ResponseEntity<ApiResponse<String>> updateIngredient(
            @RequestBody IngredientDto.UpdateIngredientReqDto update,
            @PathVariable(value = "ingredientId") Long ingredientId
    ){
        Long updateingredientId = ingredientService.updateIngredient(update,ingredientId);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(updateingredientId.toString(),CustomResponseStatus.SUCCESS));
    }
}
