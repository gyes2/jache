package com.example.jache.user.dto;

import lombok.*;


public class ChefDto {
    public class ChefRequestDto{

    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class SignUpRequestDto{
        private String chefName;
        private String password;
        private String phone;
        private String email;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class SigninRequestDto{
        private String chefName;
        private String password;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class SigninResponseDto{
        private String chefName;
        private String chefImgUrl;
        private String chefDetail;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SignUpResponseDto{
        private String chefName;
    }
}
