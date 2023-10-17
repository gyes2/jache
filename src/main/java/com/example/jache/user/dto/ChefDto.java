package com.example.jache.user.dto;

import lombok.*;


public class ChefDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignUpRequestDto{
        private String chefName;
        private String password;
        private String phone;
        private String email;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SignUpResponseDto{
        private String chefName;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SigninRequestDto{
        private String chefName;
        private String password;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SigninResponseDto{
        private String token;
        private String refresh;
    }


    @Builder
    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    public static class GetChefInfoResDto{
        private String chefName;
        private String chefDetial;
        private String chefImgUrl;
    }

    @Builder
    public static class RefreshResDto{
        private String newAccessToken;
        private String newRefreshToken;
    }

    @Builder
    @Getter
    public static class DeleteImgReqDto{
        private String chefImgUrl;
    }

    @Builder
    @Getter
    public static class DeleteImgResDto{
        private String chefImgUrl;
    }

    @Builder
    public static class UpdateImgResDto{
        private String updateImgUrl;
    }

    @Builder
    @Getter
    public static class UpdateChefDetailReqDto{
        private String chefDetails;
    }

    @Builder
    public static class UpdateChefDetailResDto{
        private String chefDetails;
    }
}
