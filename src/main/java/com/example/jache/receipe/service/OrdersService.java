package com.example.jache.receipe.service;

import com.example.jache.receipe.dto.OrderImgUploadDto;
import com.example.jache.receipe.dto.OrdersDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface OrdersService {

    boolean orderIsAuthorized(String chefName, Long receipeId);

    OrdersDto.OrdersResDto createOrders(OrderImgUploadDto orderImgUploadDto, OrdersDto.OrdersReqDto ordersReqDto) throws IOException;
}
