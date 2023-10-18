package com.example.jache.receipe.dto;

import com.example.jache.receipe.entity.Ingredient;
import lombok.*;

public class IngredientDto {

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class IngredientReqDto{
        private String ingredientName;
        private String weight;
        private Long receipeId;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class IngredientResDto{
        private Long ingredientId;
        private String ingredientName;
        private String weight;

        public IngredientResDto(Ingredient ingredient) {
            this.ingredientId = ingredient.getIngredientId();
            this.ingredientName = ingredient.getIngredientName();
            this.weight = ingredient.getWeight();
        }
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Data
    public static class UpdateIngredientReqDto{
        private String ingredientName;
        private String weight;
    }
}
