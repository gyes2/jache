package com.example.jache.receipe.dto;

import lombok.Builder;
import lombok.Getter;

public class ReceipeLoveDto {
    @Getter
    public static class LoveReqDto{
        private Long receipeId;
    }

    @Builder
    public static class LoveStatusResDto{
        private String status;
    }

}
