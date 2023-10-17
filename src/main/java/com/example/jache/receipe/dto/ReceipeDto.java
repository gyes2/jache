package com.example.jache.receipe.dto;

import com.example.jache.receipe.entity.Ingredient;
import com.example.jache.receipe.entity.Receipe;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ReceipeDto {
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class CreateReceipeReqDto{
        private Long receipeId;
        private String title;
        private String introduce;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class InitialReceipeReqDto{
        private String theme;
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
    public static class ReadReceipeDetailResDto{
        private String title;
        private String chefName;
        private int loveCount;
        private List<IngredientDto.IngredientResDto> ingredients;
        private List<OrdersDto.OrdersResDto> orders;
        private LocalDate createDate;

        public ReadReceipeDetailResDto(Receipe receipe){
            this.title = receipe.getTitle();
            this.chefName = receipe.getChef().getChefName();
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
    public static class ReadReceipeResDto{
        private String title;
        private String chefName;
        private String introduce;
        private int loveCount;
        private LocalDate createDate;
        private String imgUrl;

        public ReadReceipeResDto(Receipe receipe) {
            this.title = receipe.getTitle();
            this.chefName = receipe.getChef().getChefName();
            this.introduce = receipe.getIntroduce();
            this.loveCount = receipe.getLoveCount();
            this.createDate = receipe.getCreateDate();
            this.imgUrl = receipe.getReceipeImgUrl();
        }
    }
}
