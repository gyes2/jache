package com.example.jache.receipe.controller;

import com.example.jache.constant.dto.ApiResponse;
import com.example.jache.constant.enums.CustomResponseStatus;
import com.example.jache.receipe.dto.ImgUploadDto;
import com.example.jache.receipe.dto.OrdersDto;
import com.example.jache.receipe.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class OrdersController {

    private final OrdersService ordersService;
    @PostMapping("/orders/add")
    public ResponseEntity<ApiResponse<OrdersDto.OrdersResDto>> addOrders(
            @RequestPart(value = "ordersReqDto") OrdersDto.OrdersReqDto ordersReqDto,
            @RequestPart(value = "ordersImg") MultipartFile multipartFile, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if(ordersService.orderIsAuthorized(userDetails.getUsername(), ordersReqDto.getReceipeId())){
            ImgUploadDto orderImgUploadDto = new ImgUploadDto();
            orderImgUploadDto.setFile(multipartFile);
            OrdersDto.OrdersResDto order = null;
            try {
                order = ordersService.createOrders(orderImgUploadDto, ordersReqDto);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return ResponseEntity.ok().body(ApiResponse.createSuccess(order, CustomResponseStatus.SUCCESS));
        }
        return null;
    }

    @PutMapping("/orders/update/{orderId}")
    public ResponseEntity<ApiResponse<String>> updateOrder(
            @RequestPart(value = "ordersReqDto") OrdersDto.OrdersUpdateReqDto ordersReqDto,
            @RequestPart(value = "ordersImg") MultipartFile multipartFile,
            @PathVariable Long orderId, Authentication authentication
    ){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ImgUploadDto updateOrderImg = new ImgUploadDto();
        updateOrderImg.setFile(multipartFile);
        Long order = ordersService.updateOrders(updateOrderImg,ordersReqDto, orderId, userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccess(order.toString(),CustomResponseStatus.SUCCESS));
    }

    @DeleteMapping("/orders/delete/{ordersId}")
    public ResponseEntity<ApiResponse<String>> deleteOrders(
            @PathVariable Long ordersId, Authentication authentication
    ){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        ordersService.deleteOrders(ordersId, userDetails.getUsername());
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS));
    }
}
