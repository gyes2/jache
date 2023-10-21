package com.example.jache.receipe.dto;

import com.example.jache.receipe.entity.Ingredient;
import com.example.jache.receipe.entity.Receipe;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReceipeDto {
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Data
    public static class CreateReceipeReqDto{
        private Long receipeId;
        private String title;
        private String theme;
        private String introduce;
    }


    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class InitialReceipeResDto{
        private String chefName;
        private Long receipeId;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Getter
    public static class ReadReceipeDetailResDto{
        private Long receipeId;
        private String title;
        private String theme;
        private String chefName;
        private String introduce;
        private String receipeImgUrl;
        private int loveCount;
        private List<IngredientDto.IngredientResDto> ingredients = new ArrayList<>();
        private List<OrdersDto.OrdersResDto> orders = new ArrayList<>();
        private LocalDate createDate;

        public ReadReceipeDetailResDto(Receipe receipe){
            this.receipeId = receipe.getReceipeId();
            this.title = receipe.getTitle();
            this.theme = receipe.getTheme();
            this.chefName = receipe.getChef().getChefName();
            this.introduce = receipe.getIntroduce();
            this.receipeImgUrl = receipe.getReceipeImgUrl();
            this.loveCount = receipe.getLoveCount();
            this.ingredients = receipe.getIngredients().stream()
                    .map(IngredientDto.IngredientResDto::new)
                    .collect(Collectors.toList());
            this.orders = receipe.getOrders().stream()
                    .map(OrdersDto.OrdersResDto::new)
                    .collect(Collectors.toList());
            this.createDate = receipe.getCreateDate();
        }
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ReadReceipeResDto{
        private Long receipeId;
        private String title;
        private String chefName;
        private String introduce;
        private int loveCount;
        private LocalDate createDate;
        private String imgUrl;

        public ReadReceipeResDto(Receipe receipe) {
            this.receipeId = receipe.getReceipeId();
            this.title = receipe.getTitle();
            this.chefName = receipe.getChef().getChefName();
            this.introduce = receipe.getIntroduce();
            this.loveCount = receipe.getLoveCount();
            this.createDate = receipe.getCreateDate();
            this.imgUrl = receipe.getReceipeImgUrl();
        }
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Data
    public static class UpdateReceipeReqDto{
        private String title;
        private String introduce;
    }
}
