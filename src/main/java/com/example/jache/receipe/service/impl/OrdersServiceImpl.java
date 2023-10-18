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
        Receipe receipe = receipeRepository.findByReceipeId(ordersReqDto.getReceipeId()).orElseThrow(
                () -> new CustomException(CustomResponseStatus.RECEIPE_NOT_FOUND)
        );
        String orderUrl = "";
        if(orderImgUploadDto.getMultipartFile().isEmpty()){
            orderUrl = "https://3rdprojectbucket.s3.ap-northeast-2.amazonaws.com/initial/ordersInitial.jpg";
        }
        else{
            orderUrl = s3Service.uploadFile(orderImgUploadDto.getMultipartFile(), "orders");
        }
        Orders orders = Orders.builder()
                .receipe(receipe)
                .content(ordersReqDto.getContent())
                .contentUrl(orderUrl)
                .build();
        ordersRepository.save(orders);
        return OrdersDto.OrdersResDto.builder()
                .orderId(orders.getOrdersId())
                .content(orders.getContent())
                .contentUrl(orders.getContentUrl())
                .build();
    }

    @Override
    public Long updateOrders(ImgUploadDto updateImg, OrdersDto.OrdersUpdateReqDto updateOrder,Long orderId,String chefName) {
        Orders order = ordersRepository.findOrderByOrdersId(orderId).orElseThrow(
                () -> new CustomException(CustomResponseStatus.ORDERS_NOT_FOUND)
        );

        if(!order.getReceipe().getChef().getChefName().equals(chefName)){
            throw new CustomException(CustomResponseStatus.UNAUTHORIZED_TOKEN);
        }

        String updateImgUrl = "";
        if(updateImg.getMultipartFile().isEmpty()){
            updateImgUrl = "https://3rdprojectbucket.s3.ap-northeast-2.amazonaws.com/initial/ordersInitial.jpg";
        }
        else{
            s3Service.deleteFile(order.getContentUrl());
            updateImgUrl = s3Service.uploadFile(updateImg.getMultipartFile(),"orders");
        }
        order.modifyContentUrl(updateImgUrl);

        if(!order.getContent().equals(updateOrder.getContent())){
            order.modifyContent(updateOrder.getContent());
        }
        ordersRepository.save(order);

        return order.getOrdersId();
    }

    @Override
    public void deleteOrders(Long ordersId, String chefName) {
        Orders order = ordersRepository.findOrderByOrdersId(ordersId).orElseThrow(
                () -> new CustomException(CustomResponseStatus.ORDERS_NOT_FOUND)
        );
        if(!order.getReceipe().getChef().getChefName().equals(chefName)){
            throw new CustomException(CustomResponseStatus.UNAUTHORIZED_TOKEN);
        }
        ordersRepository.delete(order);
    }
}
