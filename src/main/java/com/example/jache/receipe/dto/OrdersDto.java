package com.example.jache.receipe.dto;

import com.example.jache.receipe.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class OrdersDto {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class OrdersReqDto{
        private String content;
        private Long receipeId;

    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class OrdersResDto{
        private Long orderId;
        private String content;
        private String contentUrl;

        public OrdersResDto(Orders orders) {
            this.orderId = orders.getOrdersId();
            this.content = orders.getContent();
            this.contentUrl = orders.getContentUrl();
        }
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class OrdersUpdateReqDto{
        private String content;
    }
}
