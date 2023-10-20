package com.example.jache.user.dto;

import com.example.jache.user.entity.Chef;
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

        public GetChefInfoResDto(Chef chef) {
            this.chefName = chef.getChefName();
            this.chefDetial = chef.getChefDetail();
            this.chefImgUrl = chef.getChefImgUrl();
        }
    }

    @Builder
    public static class RefreshResDto{
        private String newAccessToken;
        private String newRefreshToken;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeleteImgReqDto{
        private String chefImgUrl;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeleteImgResDto{
        private String chefImgUrl;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class UpdateImgResDto{
        private String updateImgUrl;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateChefDetailReqDto{
        private String chefDetails;
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class UpdateChefDetailResDto{
        private String chefDetails;
    }
}
