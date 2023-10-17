package com.example.jache.receipe.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.example.jache.constant.enums.CustomResponseStatus;
import com.example.jache.constant.exception.CustomException;
import com.example.jache.receipe.dto.ImgUploadDto;
import com.example.jache.receipe.dto.OrdersDto;
import com.example.jache.receipe.entity.Orders;
import com.example.jache.receipe.entity.Receipe;
import com.example.jache.receipe.repository.OrdersRepository;
import com.example.jache.receipe.repository.ReceipeRepository;
import com.example.jache.receipe.service.OrdersService;
import com.example.jache.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final ReceipeRepository receipeRepository;
    private final OrdersRepository ordersRepository;
    private final AmazonS3 amazonS3;
    private final S3Service s3Service;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public boolean orderIsAuthorized(String chefName, Long receipeId) {
        Receipe receipe = receipeRepository.findByReceipeId(receipeId).orElseThrow(
                () -> new CustomException(CustomResponseStatus.ORDERS_NOT_FOUND)
        );
        if(chefName.equals(receipe.getChef().getChefName())){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public OrdersDto.OrdersResDto createOrders(ImgUploadDto orderImgUploadDto, OrdersDto.OrdersReqDto ordersReqDto) {
        if(orderImgUploadDto.getMultipartFile() == null){
            String orderUrl = "https://3rdprojectbucket.s3.ap-northeast-2.amazonaws.com/initial/ordersInitial.jpg";
        }
        Orders orders = Orders.builder()
                .content(ordersReqDto.getContent())
                .contentUrl(s3Service.uploadFile(orderImgUploadDto.getMultipartFile(), "orders"))
                .build();
        ordersRepository.save(orders);
        return OrdersDto.OrdersResDto.builder()
                .content(orders.getContent())
                .contentUrl(orders.getContentUrl())
                .build();


    }
}
