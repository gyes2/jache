package com.example.jache.receipe.dto;

import lombok.*;

public class ReceipeLoveDto {
    @Getter
    public static class LoveReqDto{
        private Long receipeId;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class LoveStatusResDto{
        private String status;
    }

}
